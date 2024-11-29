package com.sparkfusion.quiz.brainvoyage.ui.screen.quiz_item

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sparkfusion.quiz.brainvoyage.ui.screen.quiz_item.component.QuizItemLoadingComponent
import com.sparkfusion.quiz.brainvoyage.ui.screen.quiz_item.component.QuizItemTopBarComponent
import com.sparkfusion.quiz.brainvoyage.ui.screen.quiz_item.component.SuccessQuizItemLoadingComponent
import com.sparkfusion.quiz.brainvoyage.ui.viewmodel.quiz_item.QuizItemContract
import com.sparkfusion.quiz.brainvoyage.ui.viewmodel.quiz_item.QuizItemViewModel
import com.sparkfusion.quiz.brainvoyage.ui.widget.reload.ReloadComponent

@Composable
fun QuizItemScreen(
    modifier: Modifier = Modifier,
    viewModel: QuizItemViewModel = hiltViewModel(),
    quizId: Long,
    onBackClick: () -> Unit
) {
    LaunchedEffect(Unit) {
        viewModel.handleIntent(QuizItemContract.Intent.ReadQuiz(quizId))
    }
    val quizLoadingState by viewModel.quizLoadingState.collectAsStateWithLifecycle()

    when (quizLoadingState) {
        QuizItemContract.QuizReadingState.Error -> {
            ReloadComponent(
                onReloadClick = { viewModel.handleIntent(QuizItemContract.Intent.ReadQuiz(quizId)) }
            ) {
                QuizItemTopBarComponent(onBackClick = onBackClick)
            }
        }

        QuizItemContract.QuizReadingState.Loading -> {
            QuizItemLoadingComponent()
        }

        is QuizItemContract.QuizReadingState.Success -> {
            SuccessQuizItemLoadingComponent(
                modifier = modifier,
                onBackClick = onBackClick,
                quiz = (quizLoadingState as QuizItemContract.QuizReadingState.Success).quiz
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun QuizItemScreenPreview() {
    QuizItemScreen(
        quizId = 1L,
        onBackClick = {}
    )
}



















