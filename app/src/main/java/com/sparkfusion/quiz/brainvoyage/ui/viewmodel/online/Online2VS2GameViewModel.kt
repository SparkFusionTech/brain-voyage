package com.sparkfusion.quiz.brainvoyage.ui.viewmodel.online

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sparkfusion.quiz.brainvoyage.domain.model.online.GamePlayersListModel
import com.sparkfusion.quiz.brainvoyage.domain.model.online.OnlineGameEvent
import com.sparkfusion.quiz.brainvoyage.domain.model.online.PlayerModel
import com.sparkfusion.quiz.brainvoyage.domain.model.question.QuestionWithAnswersModel
import com.sparkfusion.quiz.brainvoyage.domain.play_worker.XpCounter
import com.sparkfusion.quiz.brainvoyage.domain.repository.IOnline2VS2GameRepository
import com.sparkfusion.quiz.brainvoyage.utils.dispatchers.GlobalCoroutineScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class Online2VS2GameViewModel @Inject constructor(
    @GlobalCoroutineScope private val globalCoroutineScope: CoroutineScope,
    private val online2VS2GameRepository: IOnline2VS2GameRepository,
    private val xpCounter: XpCounter
) : ViewModel() {

    var answerTimeLeft: Long = 0
        private set
        get() = 20 - (System.currentTimeMillis() / 1000 - field / 1000) + 1

    private val _event = MutableSharedFlow<OnlineGameEvent?>(replay = 1)
    val event: SharedFlow<OnlineGameEvent?> get() = _event

    private val _question = MutableStateFlow<QuestionState>(QuestionState.Empty)
    val question: StateFlow<QuestionState> = _question.asStateFlow()

    private val _answerResult = MutableStateFlow<AnswerCheckState>(AnswerCheckState.Empty)
    val answerResult: StateFlow<AnswerCheckState> = _answerResult.asStateFlow()

    private val _reason = MutableStateFlow<VictoryReason>(VictoryReason.Undefined)
    val reason: StateFlow<VictoryReason> = _reason.asStateFlow()

    private val _victoryState = MutableStateFlow(VictoryState())
    val victoryState: StateFlow<VictoryState> = _victoryState.asStateFlow()

    fun createOrConnectRoom(catalogLoadingState: OnlineSelectionViewModel.QuizCatalogLoadingState) {
        viewModelScope.launch {
            online2VS2GameRepository.joinOrCreateRoom(
                when (catalogLoadingState) {
                    is OnlineSelectionViewModel.QuizCatalogLoadingState.Success -> {
                        val id = catalogLoadingState.data
                            .find { it.isSelected }?.id

                        if (id != null && id == -1L) null else id
                    }
                    else -> null
                }
            )
        }
    }

    fun disconnect() {
        viewModelScope.launch {
            online2VS2GameRepository.disconnectFromRoom()
        }
    }

    fun updateReason(reason: VictoryReason) {
        _reason.update { reason }

        if (reason is VictoryReason.GameEnd) {
            _victoryState.update {
                VictoryState(
                    account = reason.players.players[0],
                    opponent = reason.players.players[1]
                )
            }
        } else if (reason is VictoryReason.OpponentDisconnected) {
            _victoryState.update {
                VictoryState(account = reason.player)
            }
        }
    }

    fun answerQuestion() {
        if (question.value is QuestionState.Question) {
            viewModelScope.launch {
                val questionValue = (question.value as QuestionState.Question).question
                val userAnswersIds = questionValue.answers.asSequence()
                    .filter { it.isSelected }
                    .map { it.id }
                    .toList()

                val currentCorrectAnswers = questionValue.answers.asSequence()
                    .filter { it.correct }
                    .map { it.id }
                    .toList()

                val isCorrect = userAnswersIds.all { it in currentCorrectAnswers }
                online2VS2GameRepository.answerOnQuestion(
                    if (isCorrect) xpCounter.countXpForQuestion(questionValue.difficulty)
                    else 0
                )
            }
        }
    }

    fun updateSelectedAnswer(index: Int) {
        if (question.value is QuestionState.Question) {
            val questionValue = (question.value as QuestionState.Question).question
            val answers = if (questionValue.category == 2) {
                questionValue.answers.mapIndexed { i, playAnswerModel ->
                    if (index == i) playAnswerModel.copy(isSelected = !playAnswerModel.isSelected)
                    else playAnswerModel
                }
            } else {
                val value = questionValue.answers.map { playAnswerModel ->
                    playAnswerModel.copy(isSelected = false)
                }.toMutableList()
                value[index] = value[index].copy(isSelected = true)
                value.toList()
            }

            val updatedQuestion = questionValue.copy(answers = answers)
            _question.update { QuestionState.Question(updatedQuestion) }
        }
    }

    fun nextQuestion() {
        viewModelScope.launch {
            online2VS2GameRepository.nextQuestion()
        }
    }

    private fun showCorrectResult() {
        if (question.value is QuestionState.Question) {
            val questionValue = (question.value as QuestionState.Question).question
            val userAnswersIds = questionValue.answers.asSequence()
                .filter { it.isSelected }
                .map { it.id }
                .toList()

            val currentCorrectAnswers = questionValue.answers.asSequence()
                .filter { it.correct }
                .map { it.id }
                .toList()

            val isCorrect = userAnswersIds.all { it in currentCorrectAnswers }
            val incorrectAnswers = userAnswersIds.filterNot { it in currentCorrectAnswers }

            _answerResult.update {
                AnswerCheckState.Answered(
                    correctAnswersIds = currentCorrectAnswers,
                    incorrectAnswersIds = incorrectAnswers,
                    isCorrect = isCorrect
                )
            }
        }
    }

    private fun connectToRoom() {
        viewModelScope.launch {
            online2VS2GameRepository.connect()
        }
    }

    init {
        Log.d("TAGTAG", "init")
        connectToRoom()
        viewModelScope.launch {
            online2VS2GameRepository.events.collect { event ->
                Log.d("TAGTAG", event.toString())
                when (event) {
                    is OnlineGameEvent.GameStart -> {
                        _answerResult.update { AnswerCheckState.Empty }
                        _question.update { QuestionState.Question(event.question) }
                        answerTimeLeft = System.currentTimeMillis()
                    }

                    is OnlineGameEvent.NextQuestion -> {
                        _answerResult.update { AnswerCheckState.Empty }
                        _question.update { QuestionState.Question(event.question) }
                        answerTimeLeft = System.currentTimeMillis()
                    }

                    OnlineGameEvent.WaitingNextQuestion -> {
                        showCorrectResult()
                    }

                    OnlineGameEvent.AnswerTimeout -> {
                        showCorrectResult()
                    }

                    else -> {}
                }

                _event.tryEmit(event)
            }
        }
    }

    data class VictoryState(
        val account: PlayerModel? = null,
        val opponent: PlayerModel? = null
    )

    sealed interface VictoryReason {
        data object Undefined : VictoryReason
        data class OpponentDisconnected(val player: PlayerModel) : VictoryReason
        data class GameEnd(val players: GamePlayersListModel) : VictoryReason
    }

    sealed interface QuestionState {
        data object Empty : QuestionState
        data class Question(val question: QuestionWithAnswersModel) : QuestionState
    }

    override fun onCleared() {
        globalCoroutineScope.launch {
            Log.d("TAGTAG", "Cleared - " + currentCoroutineContext())
            online2VS2GameRepository.disconnectFromRoom()
            online2VS2GameRepository.disconnect()
            super.onCleared()
        }
    }
}
























