package com.sparkfusion.quiz.brainvoyage.ui.screen.quiz_item

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sparkfusion.quiz.brainvoyage.R
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
    onPlayButtonClick: () -> Unit,
    onBackClick: () -> Unit
) {
    LaunchedEffect(Unit) {
        viewModel.handleIntent(QuizItemContract.Intent.ReadQuiz(quizId))
    }
    val quizLoadingState by viewModel.quizLoadingState.collectAsStateWithLifecycle()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .paint(
                painter = painterResource(id = R.drawable.background),
                contentScale = ContentScale.Crop
            )
    ) {
        when (quizLoadingState) {
            QuizItemContract.QuizReadingState.Error -> {
                ReloadComponent(
                    onReloadClick = { viewModel.handleIntent(QuizItemContract.Intent.ReadQuiz(quizId)) }
                ) {
                    QuizItemTopBarComponent(onBackClick = onBackClick)
                }
            }

            QuizItemContract.QuizReadingState.Loading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }

            is QuizItemContract.QuizReadingState.Success -> {
                SuccessQuizItemLoadingComponent(
                    modifier = modifier,
                    onBackClick = onBackClick,
                    onPlayButtonClick = onPlayButtonClick,
                    quiz = (quizLoadingState as QuizItemContract.QuizReadingState.Success).quiz
                )
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun QuizItemScreenPreview() {
    QuizItemScreen(
        quizId = 1L,
        onBackClick = {},
        onPlayButtonClick = {}
    )
}



















