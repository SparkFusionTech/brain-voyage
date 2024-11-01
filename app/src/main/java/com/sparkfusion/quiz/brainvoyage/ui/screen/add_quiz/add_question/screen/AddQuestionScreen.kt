package com.sparkfusion.quiz.brainvoyage.ui.screen.add_quiz.add_question.screen

import android.graphics.Bitmap
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sparkfusion.quiz.brainvoyage.R
import com.sparkfusion.quiz.brainvoyage.ui.launcher.rememberLauncherForImageCropping
import com.sparkfusion.quiz.brainvoyage.ui.screen.add_quiz.add_question.component.button.AddButtonComponent
import com.sparkfusion.quiz.brainvoyage.ui.screen.add_quiz.add_question.component.button.SaveButton
import com.sparkfusion.quiz.brainvoyage.ui.screen.add_quiz.add_question.component.category.CategoryComponent
import com.sparkfusion.quiz.brainvoyage.ui.screen.add_quiz.add_question.component.difficulty.DifficultyComponent
import com.sparkfusion.quiz.brainvoyage.ui.screen.add_quiz.add_question.component.handler.HandleQuestionAddingComponent
import com.sparkfusion.quiz.brainvoyage.ui.screen.add_quiz.add_question.component.image.QuestionImageComponent
import com.sparkfusion.quiz.brainvoyage.ui.screen.add_quiz.add_question.component.input.QuestionNameInputComponent
import com.sparkfusion.quiz.brainvoyage.ui.screen.add_quiz.add_question.component.item.AnswerItemComponent
import com.sparkfusion.quiz.brainvoyage.ui.screen.add_quiz.add_question.component.text.AnswersTitleComponent
import com.sparkfusion.quiz.brainvoyage.ui.screen.add_quiz.add_question.component.text.ExplanationComponent
import com.sparkfusion.quiz.brainvoyage.ui.screen.add_quiz.add_question.component.topbar.AddQuestionTopBar
import com.sparkfusion.quiz.brainvoyage.ui.screen.add_quiz.add_question.model.category.CategoryType
import com.sparkfusion.quiz.brainvoyage.ui.viewmodel.add_quiz.add_question.add.AddQuestionContract
import com.sparkfusion.quiz.brainvoyage.ui.viewmodel.add_quiz.add_question.add.AddQuestionViewModel
import com.sparkfusion.quiz.brainvoyage.ui.viewmodel.add_quiz.add_question.shared.SharedQuestionContract
import com.sparkfusion.quiz.brainvoyage.ui.viewmodel.add_quiz.add_question.shared.SharedQuestionsViewModel
import com.sparkfusion.quiz.brainvoyage.ui.widget.SFProRoundedText
import com.sparkfusion.quiz.brainvoyage.ui.widget.dialog.add_answer.AddAnswerDialog
import com.sparkfusion.quiz.brainvoyage.ui.widget.dialog.close_adding.CloseQuizAddingDialog
import com.sparkfusion.quiz.brainvoyage.ui.widget.dialog.select_image.SelectImageDialog

@Composable
fun AddQuestionScreen(
    modifier: Modifier = Modifier,
    viewModel: AddQuestionViewModel = hiltViewModel(),
    sharedQuestionsViewModel: SharedQuestionsViewModel,
    onDismiss: () -> Unit,
    onSearchImageScreenNavigate: () -> Unit,
    onImageCropNavigate: (Bitmap) -> Unit,
    onReadCroppedImage: () -> Bitmap?
) {
    viewModel.handleIntent(AddQuestionContract.Intent.ChangeIcon(onReadCroppedImage()))
    val state by viewModel.commonState.collectAsStateWithLifecycle()
    val imageState by viewModel.imageState.collectAsStateWithLifecycle()
    val answers by viewModel.answersState.collectAsStateWithLifecycle()
    val dialogs by viewModel.dialogsState.collectAsStateWithLifecycle()
    val adding by viewModel.errorState.collectAsStateWithLifecycle()

    val snackbarHostState = remember { SnackbarHostState() }
    val context = LocalContext.current
    val galleryLauncher = rememberLauncherForImageCropping(
        context = context,
        onImageCropNavigate
    )

    val changeNewAnswerDialogVisibility = { v: Boolean ->
        viewModel.handleIntent(AddQuestionContract.Intent.ChangeNewAnswerDialogVisibility(v))
    }
    val changeCloseDialogVisibility = { v: Boolean ->
        viewModel.handleIntent(AddQuestionContract.Intent.ChangeCloseDialogVisibility(v))
    }
    val changeImageSelectionDialogVisibility = { v: Boolean ->
        viewModel.handleIntent(AddQuestionContract.Intent.ChangeImageSelectionDialogVisibility(v))
    }

    CloseQuizAddingDialog(
        show = dialogs.showCloseDialog,
        onDismiss = {
            changeCloseDialogVisibility(false)
        },
        onConfirm = {
            onDismiss()
        }
    )

    SelectImageDialog(
        show = dialogs.showImageSelectionDialog,
        onSearchNavigate = {
            onSearchImageScreenNavigate()
            changeImageSelectionDialogVisibility(false)
        },
        onGalleryNavigate = {
            galleryLauncher.launch("image/*")
            changeImageSelectionDialogVisibility(false)
        },
        onDismiss = {
            changeImageSelectionDialogVisibility(false)
        }
    )

    AddAnswerDialog(
        show = dialogs.showNewAnswerDialog,
        snackbarHostState = snackbarHostState,
        onConfirm = { answer ->
            viewModel.handleIntent(AddQuestionContract.Intent.AddAnswer(answer))
            changeNewAnswerDialogVisibility(false)
        },
        onDismiss = {
            changeNewAnswerDialogVisibility(false)
        }
    )

    Scaffold(
        snackbarHost = {
            SnackbarHost(
                hostState = snackbarHostState,
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 12.dp)
            )
        },
        topBar = {
            AddQuestionTopBar(
                onBackClick = {
                    changeCloseDialogVisibility(true)
                }
            )
        }
    ) {
        HandleQuestionAddingComponent(
            state = adding,
            snackbarHostState = snackbarHostState,
            onSuccess = { model ->
                sharedQuestionsViewModel.handleIntent(
                    SharedQuestionContract.Intent.AddQuestion(model)
                )
                onDismiss()
            },
            onClearState = {
                viewModel.handleIntent(AddQuestionContract.Intent.ClearQuestionAddingState)
            }
        )

        LazyColumn(
            modifier = modifier
                .padding(it)
                .fillMaxWidth()
        ) {
            item {
                QuestionImageComponent(
                    bitmap = imageState.bitmap,
                    changeImageSelectionDialogVisibility = changeImageSelectionDialogVisibility
                )

                QuestionNameInputComponent(
                    snackbarHostState = snackbarHostState,
                    value = state.question,
                    onValueChange = { question ->
                        viewModel.handleIntent(AddQuestionContract.Intent.ChangeQuestion(question))
                    }
                )

                SFProRoundedText(
                    modifier = Modifier.padding(start = 24.dp, top = 20.dp),
                    content = stringResource(id = R.string.category),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold
                )

                CategoryComponent(
                    categories = viewModel.categories,
                    onListClick = { value ->
                        viewModel.handleIntent(
                            AddQuestionContract.Intent.ChangeCategoryListVisibility(value)
                        )
                    },
                    isListVisible = state.isCategoryListVisible,
                    onItemClick = { index ->
                        viewModel.handleIntent(
                            AddQuestionContract.Intent.ChangeCategory(
                                index,
                                context.resources.getStringArray(R.array.trueFalseValues).toList()
                            )
                        )
                    },
                    currentCategoryId = state.currentCategoryId
                )

                AnswersTitleComponent(subtitle = stringResource(id = R.string.max_4))
            }

            items(answers.size) { index ->
                AnswerItemComponent(
                    index = index,
                    categoryType = viewModel.categories[state.currentCategoryId].type,
                    answer = answers[index],
                    onRadioButtonClick = { id ->
                        viewModel.handleIntent(
                            AddQuestionContract.Intent.ChangeSelectedRadioButton(id)
                        )
                    },
                    onCheckButtonClick = { id, value ->
                        viewModel.handleIntent(
                            AddQuestionContract.Intent.ChangeSelectedCheckButton(id, value)
                        )
                    }
                )
            }

            item {
                if (viewModel.categories[state.currentCategoryId].type != CategoryType.TrueFalse) {
                    if (answers.size < 8) {
                        AddButtonComponent {
                            changeNewAnswerDialogVisibility(true)
                        }
                    }

                    DifficultyComponent(
                        difficulties = viewModel.difficulties,
                        currentDifficultyId = state.currentDifficultyId,
                        onItemClick = { index ->
                            viewModel.handleIntent(AddQuestionContract.Intent.ChangeDifficulty(index))
                        }
                    )
                }

                ExplanationComponent(
                    description = state.description,
                    onValueChange = { description ->
                        viewModel.handleIntent(
                            AddQuestionContract.Intent.ChangeDescription(description)
                        )
                    }
                )

                SaveButton {
                    viewModel.handleIntent(AddQuestionContract.Intent.HandleQuestionAdding)
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun AddQuestionScreenPreview() {
    AddQuestionScreen(
        onDismiss = {},
        onSearchImageScreenNavigate = {},
        onImageCropNavigate = {},
        onReadCroppedImage = { Bitmap.createBitmap(180, 200, Bitmap.Config.RGB_565) },
        sharedQuestionsViewModel = SharedQuestionsViewModel()
    )
}


























