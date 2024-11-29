package com.sparkfusion.quiz.brainvoyage.ui.viewmodel.drawer.my_quizzes

import androidx.lifecycle.viewModelScope
import com.sparkfusion.quiz.brainvoyage.domain.model.quiz.SubmittedQuizModel
import com.sparkfusion.quiz.brainvoyage.domain.repository.IQuizRepository
import com.sparkfusion.quiz.brainvoyage.utils.common.viewmodel.MultiStateViewModel
import com.sparkfusion.quiz.brainvoyage.utils.dispatchers.DefaultDispatcher
import com.sparkfusion.quiz.brainvoyage.utils.dispatchers.IODispatcher
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class MyQuizzesViewModel @Inject constructor(
    @IODispatcher private val ioDispatcher: CoroutineDispatcher,
    @DefaultDispatcher private val defaultDispatcher: CoroutineDispatcher,
    private val quizRepository: IQuizRepository
) : MultiStateViewModel<MyQuizzesContract.Intent>() {

    private val _submittedQuizzesState = MutableStateFlow<MyQuizzesContract.QuizzesReadingState>(
        MyQuizzesContract.QuizzesReadingState.Loading
    )
    val submittedQuizzesState: StateFlow<MyQuizzesContract.QuizzesReadingState>
        get() = _submittedQuizzesState.asStateFlow()

    override fun handleIntent(intent: MyQuizzesContract.Intent) {
        when (intent) {
            MyQuizzesContract.Intent.ReadQuizzes -> readQuizzes()
        }
    }

    private fun readQuizzes() {
        _submittedQuizzesState.update { MyQuizzesContract.QuizzesReadingState.Loading }
        viewModelScope.launch(ioDispatcher) {
            quizRepository.readSubmittedQuizzes()
                .onSuccess {
                    val quizzesMap = mapQuizzesListToMap(it)
                    _submittedQuizzesState.update { MyQuizzesContract.QuizzesReadingState.Success(quizzesMap) }
                }
                .onFailure {
                    _submittedQuizzesState.update { MyQuizzesContract.QuizzesReadingState.Error }
                }
        }
    }

    private suspend fun mapQuizzesListToMap(quizzes: List<SubmittedQuizModel>): Map<String, List<SubmittedQuizModel>> {
        val formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy")
        return withContext(defaultDispatcher) {
            quizzes.groupBy { it.createdAt.format(formatter) }
        }
    }
}

























