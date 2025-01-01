package com.sparkfusion.quiz.brainvoyage.ui.screen.drawer.my_quiz_info

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sparkfusion.quiz.brainvoyage.ui.screen.drawer.my_quiz_info.component.MyQuizInfoTopBar
import com.sparkfusion.quiz.brainvoyage.ui.screen.drawer.my_quiz_info.component.SuccessQuizReadingComponent
import com.sparkfusion.quiz.brainvoyage.ui.theme.settingsBackgroundDarkColor
import com.sparkfusion.quiz.brainvoyage.ui.theme.settingsBackgroundLightColor
import com.sparkfusion.quiz.brainvoyage.ui.viewmodel.drawer.my_quiz_info.MyQuizInfoContract
import com.sparkfusion.quiz.brainvoyage.ui.viewmodel.drawer.my_quiz_info.MyQuizInfoViewModel
import com.sparkfusion.quiz.brainvoyage.utils.dp.getStatusBarHeightInDp
import com.sparkfusion.quiz.brainvoyage.window.StatusBarHeightOwner

@Composable
fun MyQuizInfoScreen(
    modifier: Modifier = Modifier,
    viewModel: MyQuizInfoViewModel = hiltViewModel(),
    quizId: Long,
    onBackClick: () -> Unit
) {
    val readQuizState by viewModel.quizReadingState.collectAsStateWithLifecycle()
    val readTagsState by viewModel.tagsReadingState.collectAsStateWithLifecycle()
    val readQuestionState by viewModel.questionsReadingState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.handleIntent(MyQuizInfoContract.Intent.ReadQuiz(quizId))
    }

    val context = LocalContext.current

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(
                Brush.linearGradient(
                    listOf(
                        settingsBackgroundLightColor,
                        settingsBackgroundDarkColor
                    )
                )
            )
    ) {
        item {
            MyQuizInfoTopBar(
                modifier = Modifier.padding(
                    top = if (StatusBarHeightOwner.hasCutout) getStatusBarHeightInDp().dp else 0.dp
                ),
                onBackClick = onBackClick,
                onDeleteClick = {}
            )
        }

        when (readQuizState) {
            MyQuizInfoContract.QuizReadingState.Error -> {
                Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show()
            }

            MyQuizInfoContract.QuizReadingState.Loading -> {
                item {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
            }

            is MyQuizInfoContract.QuizReadingState.Success -> {
                item {
                    SuccessQuizReadingComponent(
                        quiz = (readQuizState as MyQuizInfoContract.QuizReadingState.Success).quiz,
                        tagsState = readTagsState,
                        questionsState = readQuestionState
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun MyQuizInfoScreenPreview() {
    MyQuizInfoScreen(
        onBackClick = {},
        quizId = 1
    )
}






















