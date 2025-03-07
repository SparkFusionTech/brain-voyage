package com.sparkfusion.quiz.brainvoyage.ui.viewmodel.victory

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.sparkfusion.quiz.brainvoyage.domain.model.quiz.rating.UserQuizRatingModel
import com.sparkfusion.quiz.brainvoyage.domain.repository.IAccountInfoStore
import com.sparkfusion.quiz.brainvoyage.domain.repository.IQuizRatingRepository
import com.sparkfusion.quiz.brainvoyage.utils.common.viewmodel.MultiStateViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class VictoryQuizViewModel @Inject constructor(
    private val accountInfoStore: IAccountInfoStore,
    private val quizRatingRepository: IQuizRatingRepository
) : MultiStateViewModel<VictoryQuizContract.VictoryQuizIntent>() {

    private val _initialState = MutableStateFlow<VictoryQuizContract.InitialState?>(null)
    val initialState: StateFlow<VictoryQuizContract.InitialState?> get() = _initialState.asStateFlow()

    private val _accountInfo = MutableStateFlow(AccountInfo())
    val accountInfo: StateFlow<AccountInfo> = _accountInfo.asStateFlow()

    private val _ratingState = MutableStateFlow<RatingState>(RatingState.Initial)
    val ratingState: StateFlow<RatingState> = _ratingState.asStateFlow()

    private val _updateRatingState = MutableStateFlow<UpdateRatingState>(UpdateRatingState.Initial)
    val updateRatingState: StateFlow<UpdateRatingState> = _updateRatingState.asStateFlow()

    override fun handleIntent(intent: VictoryQuizContract.VictoryQuizIntent) {
        when (intent) {
            is VictoryQuizContract.VictoryQuizIntent.InitVictoryScreen -> initState(intent.state)
            VictoryQuizContract.VictoryQuizIntent.ReadAccountInfo -> readAccountInfo()
            is VictoryQuizContract.VictoryQuizIntent.ReadUserQuizRating -> readUserQuizRating(intent.quizId)
            is VictoryQuizContract.VictoryQuizIntent.UpdateRating -> updateRating(intent.rating)
            VictoryQuizContract.VictoryQuizIntent.ClearRatingUpdatingState -> clearRatingUpdatingState()
        }
    }

    fun formatNextTryTime(nextTryDateTime: LocalDateTime): String {
        val formatter = DateTimeFormatter.ofPattern("HH:mm")
        return nextTryDateTime.format(formatter)
    }

    private fun clearRatingUpdatingState() {
        _updateRatingState.update { UpdateRatingState.Initial }
    }

    private fun updateRating(rating: Int) {
        if (initialState.value != null) {
            val quizId = initialState.value!!.quizId
            viewModelScope.launch {
                quizRatingRepository.updateRating(quizId, rating)
                    .onSuccess {
                        readUserQuizRating(quizId)
                    }
                    .onFailure {
                        Log.d("TAGTAG", it.message.toString())
                        _updateRatingState.update { UpdateRatingState.Error }
                    }
            }
        }
    }

    private fun readUserQuizRating(quizId: Long) {
        if (ratingState.value == RatingState.Progress) return

        _ratingState.update { RatingState.Progress }
        viewModelScope.launch {
            quizRatingRepository.readUserQuizRating(quizId)
                .onSuccess { model ->
                    _ratingState.update { RatingState.Success(model) }
                }
                .onFailure {
                    Log.d("TAGTAG", it.message.toString())
                    _ratingState.update { RatingState.Error }
                }
        }
    }

    private fun readAccountInfo() {
        viewModelScope.launch {
            accountInfoStore.readAccountInfo().collectLatest { info ->
                Log.d("TAGTAG", info.toString())
                _accountInfo.update { it.copy(accountUrl = info.iconUrl, name = info.name) }
            }
        }
    }

    private fun initState(state: VictoryQuizContract.InitialState) {
        _initialState.update { state }
        readAccountInfo()
    }

    data class AccountInfo(
        val accountUrl: String = "",
        val name: String = ""
    )

    sealed interface RatingState {
        data object Initial : RatingState
        data object Progress : RatingState
        data object Error : RatingState
        data class Success(val rating: UserQuizRatingModel?) : RatingState
    }

    sealed interface UpdateRatingState {
        data object Initial : UpdateRatingState
        data object Error : UpdateRatingState
    }
}


















