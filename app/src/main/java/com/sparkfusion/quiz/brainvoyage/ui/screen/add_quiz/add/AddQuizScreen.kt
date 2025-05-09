package com.sparkfusion.quiz.brainvoyage.ui.screen.add_quiz.add

import android.graphics.Bitmap
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sparkfusion.quiz.brainvoyage.ui.dialog.add_tag.AddTagBottomSheet
import com.sparkfusion.quiz.brainvoyage.ui.dialog.select_image.SelectImageBottomSheet
import com.sparkfusion.quiz.brainvoyage.ui.launcher.rememberLauncherForImageCropping
import com.sparkfusion.quiz.brainvoyage.ui.model.QuizCatalogSerializable
import com.sparkfusion.quiz.brainvoyage.ui.screen.add_quiz.add.component.AddQuizContent
import com.sparkfusion.quiz.brainvoyage.ui.screen.add_quiz.add.component.AddQuizTopComponent
import com.sparkfusion.quiz.brainvoyage.ui.screen.add_quiz.add.handler.SendModelHandler
import com.sparkfusion.quiz.brainvoyage.ui.screen.add_quiz.model.AddQuizInitialModel
import com.sparkfusion.quiz.brainvoyage.ui.theme.settingsBackgroundDarkColor
import com.sparkfusion.quiz.brainvoyage.ui.theme.settingsBackgroundLightColor
import com.sparkfusion.quiz.brainvoyage.ui.viewmodel.add_quiz.add.AddQuizContract
import com.sparkfusion.quiz.brainvoyage.ui.viewmodel.add_quiz.add.AddQuizViewModel
import com.sparkfusion.quiz.brainvoyage.ui.viewmodel.add_quiz.add.SharedQuizContract
import com.sparkfusion.quiz.brainvoyage.ui.viewmodel.add_quiz.add.SharedQuizViewModel
import com.sparkfusion.quiz.brainvoyage.utils.dp.getStatusBarHeightInDp
import com.sparkfusion.quiz.brainvoyage.window.StatusBarHeightOwner

@Composable
fun AddQuizScreen(
    modifier: Modifier = Modifier,
    quizCatalogSerializable: QuizCatalogSerializable,
    viewModel: AddQuizViewModel = hiltViewModel(),
    sharedQuizViewModel: SharedQuizViewModel,
    onBackClick: () -> Unit,
    onNextClick: (AddQuizInitialModel) -> Unit,
    onSearchImageScreenNavigate: () -> Unit,
    onImageCropNavigate: (Bitmap) -> Unit,
    getCroppedImageBitmap: () -> Bitmap?
) {
    LaunchedEffect(Unit) {
        viewModel.handleIntent(AddQuizContract.AddQuizIntent.ChangeIcon(getCroppedImageBitmap()))
        viewModel.handleIntent(AddQuizContract.AddQuizIntent.SetCatalog(quizCatalogSerializable))
    }

    val state by viewModel.state.collectAsStateWithLifecycle()
    val tags by viewModel.tagsState.collectAsStateWithLifecycle()
    val sendModelState by viewModel.sendModelState.collectAsStateWithLifecycle()

    val changeImageSelectionDialogVisibility = { v: Boolean ->
        viewModel.handleIntent(AddQuizContract.AddQuizIntent.ChangeImageSelectionDialogVisibility(v))
    }
    val changeTagAddingDialogVisibility = { v: Boolean ->
        viewModel.handleIntent(AddQuizContract.AddQuizIntent.ChangeTagAddingDialogVisibility(v))
    }

    val context = LocalContext.current
    val galleryLauncher = rememberLauncherForImageCropping(
        context = context,
        onImageCropNavigate
    )
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        topBar = {
            AddQuizTopComponent(
                modifier = Modifier.padding(
                    top = if (StatusBarHeightOwner.hasCutout) getStatusBarHeightInDp().dp else 0.dp
                ),
                onBackClick = onBackClick,
                onTagSearchClick = {
                    changeTagAddingDialogVisibility(true)
                }
            )
        },
        snackbarHost = {
            SnackbarHost(
                hostState = snackbarHostState,
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 12.dp)
            )
        }
    ) {
        SendModelHandler(
            sendModelState = sendModelState,
            snackbarHostState = snackbarHostState,
            onSuccess = { model ->
                sharedQuizViewModel.handleIntent(
                    SharedQuizContract.Intent.SetAddQuizInitialModel(model)
                )
                onNextClick(model)
            },
            clearState = {
                viewModel.handleIntent(AddQuizContract.AddQuizIntent.ClearSendQuizState)
            }
        )

        AddQuizContent(
            modifier = modifier
                .background(Brush.linearGradient(listOf(settingsBackgroundLightColor, settingsBackgroundDarkColor)))
                .padding(it),
            bitmap = state.bitmap,
            onImageAddClick = { changeImageSelectionDialogVisibility(true) },
            title = state.title,
            description = state.description,
            onTitleChange = { title ->
                viewModel.handleIntent(
                    AddQuizContract.AddQuizIntent.ChangeTitle(title)
                )
            },
            onDescriptionChange = { description ->
                viewModel.handleIntent(
                    AddQuizContract.AddQuizIntent.ChangeDescription(description)
                )
            },
            onNextButtonClick = { viewModel.handleIntent(AddQuizContract.AddQuizIntent.LoadSendQuizState) }
        )
    }

    SelectImageBottomSheet(
        show = state.showImageSelectionDialog,
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

    AddTagBottomSheet(
        show = state.showTagAddingDialog,
        items = tags,
        onDismiss = {
            changeTagAddingDialogVisibility(false)
        },
        onAddTag = {
            viewModel.handleIntent(AddQuizContract.AddQuizIntent.AddTag(it))
        },
        onDeleteTag = {
            viewModel.handleIntent(AddQuizContract.AddQuizIntent.DeleteTag(it))
        }
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun AddQuizScreenPreview() {
    AddQuizScreen(
        onBackClick = {},
        onNextClick = {},
        onSearchImageScreenNavigate = {},
        onImageCropNavigate = {},
        getCroppedImageBitmap = { null },
        sharedQuizViewModel = SharedQuizViewModel(),
        quizCatalogSerializable = QuizCatalogSerializable(1, "")
    )
}
































