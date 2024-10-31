package com.sparkfusion.quiz.brainvoyage.ui.screen.add_quiz.questions

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.sparkfusion.quiz.brainvoyage.R
import com.sparkfusion.quiz.brainvoyage.ui.screen.add_quiz.questions.component.HandlePublicationComponent
import com.sparkfusion.quiz.brainvoyage.ui.viewmodel.add_quiz.add.SharedQuizViewModel
import com.sparkfusion.quiz.brainvoyage.ui.viewmodel.add_quiz.add_question.shared.SharedQuestionsViewModel
import com.sparkfusion.quiz.brainvoyage.ui.viewmodel.add_quiz.questions.AddQuizWithQuestionsContract
import com.sparkfusion.quiz.brainvoyage.ui.viewmodel.add_quiz.questions.AddQuizWithQuestionsViewModel
import com.sparkfusion.quiz.brainvoyage.ui.widget.SFProRoundedText
import com.sparkfusion.quiz.brainvoyage.ui.widget.dialog.close_adding.CloseQuizAddingDialog
import com.sparkfusion.quiz.brainvoyage.ui.widget.star.QuestionDifficulty
import com.sparkfusion.quiz.brainvoyage.ui.widget.star.StarCanvas
import com.sparkfusion.quiz.brainvoyage.utils.descriptionColor
import com.sparkfusion.quiz.brainvoyage.utils.primaryGradientWithAlpha

@Composable
fun AddQuizWithQuestionScreen(
    modifier: Modifier = Modifier,
    viewModel: AddQuizWithQuestionsViewModel = hiltViewModel(),
    sharedQuizViewModel: SharedQuizViewModel,
    sharedQuestionsViewModel: SharedQuestionsViewModel,
    onBackClick: () -> Unit,
    onAddQuestionClick: () -> Unit
) {
    val model by sharedQuizViewModel.quizModel.collectAsStateWithLifecycle()
    val questions by sharedQuestionsViewModel.questions.collectAsStateWithLifecycle()
    val state by viewModel.state.collectAsStateWithLifecycle()
    val publishState by viewModel.publishState.collectAsStateWithLifecycle()
    val savingState by viewModel.quizAddingState.collectAsStateWithLifecycle()

    val snackbarHostState = remember { SnackbarHostState() }
    val changeCloseDialogVisibility = { v: Boolean ->
        viewModel.handleIntent(AddQuizWithQuestionsContract.Intent.ChangeCloseDialogVisibility(v))
    }
    val clearPublishState = { viewModel.handleIntent(AddQuizWithQuestionsContract.Intent.ClearPublishState) }

    LaunchedEffect(savingState) {
        when (savingState) {
            AddQuizWithQuestionsContract.QuizSavingState.Empty -> {}
            AddQuizWithQuestionsContract.QuizSavingState.Error -> {
                snackbarHostState.showSnackbar("Error")
            }

            AddQuizWithQuestionsContract.QuizSavingState.QuestionsSaving -> {
            }
            AddQuizWithQuestionsContract.QuizSavingState.QuizSaving -> {}
            AddQuizWithQuestionsContract.QuizSavingState.TagsSaving -> {}
            AddQuizWithQuestionsContract.QuizSavingState.Success -> {
                snackbarHostState.showSnackbar("It is correct")
            }
        }
    }

    BackHandler { changeCloseDialogVisibility(true) }
    HandlePublicationComponent(
        publishState = publishState,
        snackbarHostState = snackbarHostState,
        clearPublishState = clearPublishState
    )

    CloseQuizAddingDialog(
        show = state.showCloseDialog,
        onDismiss = {
            changeCloseDialogVisibility(false)
        },
        onConfirm = {
            onBackClick()
        }
    )

    Scaffold(
        snackbarHost = {
            SnackbarHost(
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 12.dp),
                hostState = snackbarHostState
            )
        },
        topBar = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = modifier
                    .height(72.dp)
                    .fillMaxWidth()
            ) {
                IconButton(
                    modifier = Modifier.padding(start = 8.dp),
                    onClick = { changeCloseDialogVisibility(true) }
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.arrow_back_icon),
                        contentDescription = null
                    )
                }

                Spacer(modifier = Modifier.weight(1f))

                SFProRoundedText(
                    content = "Questions",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.ExtraBold
                )

                Spacer(modifier = Modifier.weight(1f))

                IconButton(
                    onClick = {
                        viewModel.handleIntent(
                            AddQuizWithQuestionsContract.Intent.SaveQuiz(
                                addQuizInitialModel = model,
                                questions = questions
                            )
                        )
                    }
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.round_check),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }
    ) {
        if (model == null) CircularProgressIndicator()
        else
            LazyColumn(
                modifier = modifier
                    .padding(it)
                    .fillMaxWidth()
            ) {
                item {
                    Column {
                        Spacer(modifier = Modifier.height(30.dp))
                        AsyncImage(
                            modifier = Modifier
                                .align(Alignment.CenterHorizontally)
                                .clip(RoundedCornerShape(16.dp))
                                .height(200.dp)
                                .width(180.dp),
                            model = model!!.bitmap,
                            contentScale = ContentScale.Crop,
                            contentDescription = null
                        )

                        SFProRoundedText(
                            modifier = Modifier
                                .padding(start = 24.dp, end = 24.dp, top = 16.dp)
                                .fillMaxWidth(),
                            content = model!!.title,
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Black,
                            textAlign = TextAlign.Center
                        )

                        SFProRoundedText(
                            modifier = Modifier
                                .padding(start = 24.dp, end = 24.dp, top = 16.dp)
                                .fillMaxWidth(),
                            content = model!!.description,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.SemiBold,
                        )

                        SFProRoundedText(
                            modifier = Modifier
                                .padding(start = 24.dp, end = 24.dp, top = 24.dp, bottom = 4.dp)
                                .fillMaxWidth(),
                            content = "Add questions only on the selected topic, otherwise the quiz will not pass moderation!*",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium,
                        )
                    }
                }

                items(questions.size) { index ->
                    Row(
                        modifier = Modifier
                            .padding(start = 24.dp, end = 24.dp, top = 2.dp, bottom = 4.dp)
                            .border(
                                width = 2.dp,
                                color = MaterialTheme.colorScheme.primary,
                                shape = RoundedCornerShape(20.dp)
                            )
                            .background(
                                brush = primaryGradientWithAlpha(),
                                shape = RoundedCornerShape(20.dp),
                                alpha = 0.1f
                            )
                            .height(96.dp)
                            .fillMaxWidth()
                    ) {
                        AsyncImage(
                            modifier = Modifier
                                .padding(24.dp)
                                .clip(RoundedCornerShape(16.dp))
                                .size(48.dp),
                            model = questions[index].icon,
                            contentScale = ContentScale.Fit,
                            contentDescription = null
                        )

                        Column(
                            modifier = Modifier.padding(),
                            verticalArrangement = Arrangement.Center,
                        ) {
                            Row(
                                modifier = Modifier.padding(top = 24.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Start
                            ) {
                                SFProRoundedText(
                                    content = questions[index].name,
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )

                                StarCanvas(
                                    modifier = Modifier.padding(start = 4.dp),
                                    sizeDp = 28.dp,
                                    cornerRadiusDp = 2.dp,
                                    difficulty = QuestionDifficulty.Easy
                                )
                            }

                            SFProRoundedText(
                                content = "5 answer options",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Medium,
                                color = descriptionColor()
                            )
                        }
                    }
                }

                item {
                    Button(
                        modifier = Modifier
                            .padding(horizontal = 120.dp, vertical = 20.dp)
                            .fillMaxWidth(),
                        onClick = onAddQuestionClick
                    ) {
                        SFProRoundedText(
                            content = "Add",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun AddQuizWithQuestionsScreenPreview() {
    AddQuizWithQuestionScreen(
        onBackClick = {},
        onAddQuestionClick = {},
        sharedQuizViewModel = SharedQuizViewModel(),
        sharedQuestionsViewModel = SharedQuestionsViewModel()
    )
}
























