package com.sparkfusion.quiz.brainvoyage.ui.screen.add_quiz.add

import android.graphics.Bitmap
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.sparkfusion.quiz.brainvoyage.R
import com.sparkfusion.quiz.brainvoyage.ui.launcher.rememberLauncherForImageCropping
import com.sparkfusion.quiz.brainvoyage.ui.model.QuizCatalogSerializable
import com.sparkfusion.quiz.brainvoyage.ui.screen.add_quiz.model.AddQuizInitialModel
import com.sparkfusion.quiz.brainvoyage.ui.viewmodel.add_quiz.add.AddQuizContract
import com.sparkfusion.quiz.brainvoyage.ui.viewmodel.add_quiz.add.AddQuizViewModel
import com.sparkfusion.quiz.brainvoyage.ui.viewmodel.add_quiz.add.SharedQuizContract
import com.sparkfusion.quiz.brainvoyage.ui.viewmodel.add_quiz.add.SharedQuizViewModel
import com.sparkfusion.quiz.brainvoyage.ui.widget.SFProRoundedText
import com.sparkfusion.quiz.brainvoyage.ui.widget.dialog.add_tag.AddTagDialog
import com.sparkfusion.quiz.brainvoyage.ui.widget.dialog.select_image.SelectImageDialog
import com.sparkfusion.quiz.brainvoyage.ui.widget.text.DefaultTextField
import com.sparkfusion.quiz.brainvoyage.ui.widget.text.DescriptionText
import com.sparkfusion.quiz.brainvoyage.ui.widget.text.TitleText
import com.sparkfusion.quiz.brainvoyage.utils.descriptionColor

//internal const val SEND_QUIZ_KEY = "send quiz key"
internal const val SEND_QUESTION_KEY = "send question key"

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
    viewModel.handleIntent(AddQuizContract.AddQuizIntent.ChangeIcon(getCroppedImageBitmap()))

    LaunchedEffect(Unit) {
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

    SelectImageDialog(
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

    AddTagDialog(
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

    Scaffold(
        topBar = {
            AddQuizTopComponent(
                modifier = Modifier,
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
                sharedQuizViewModel.handleIntent(SharedQuizContract.Intent.SetAddQuizInitialModel(model))
                onNextClick(model)
            },
            clearState = {
                viewModel.handleIntent(AddQuizContract.AddQuizIntent.ClearSendQuizState)
            }
        )

        Column(
            modifier = modifier
                .padding(it)
                .fillMaxSize()
        ) {
            Spacer(modifier = Modifier.height(30.dp))
            AsyncImage(
                modifier = Modifier
                    .clickable { changeImageSelectionDialogVisibility(true) }
                    .align(Alignment.CenterHorizontally)
                    .clip(RoundedCornerShape(16.dp))
                    .height(200.dp)
                    .width(180.dp),
                model = if (state.bitmap == null) R.drawable.select_image_icon else state.bitmap,
                contentScale = ContentScale.Fit,
                contentDescription = null
            )

            Row(
                modifier = Modifier.padding(start = 24.dp, end = 24.dp, top = 40.dp)
            ) {
                TitleText(content = "Title")

                SFProRoundedText(
                    modifier = Modifier.padding(start = 4.dp),
                    content = "(max 32)",
                    fontWeight = FontWeight.Medium,
                    color = descriptionColor()
                )
            }

            DefaultTextField(
                modifier = Modifier
                    .padding(start = 24.dp, end = 24.dp)
                    .fillMaxWidth(),
                value = state.title,
                keyboardType = KeyboardType.Text,
                onValueChange = { title ->
                    viewModel.handleIntent(
                        AddQuizContract.AddQuizIntent.ChangeTitle(title)
                    )
                },
                singleLine = true,
                placeholder = stringResource(id = R.string.enter_here)
            )

            DescriptionText(
                modifier = Modifier.padding(start = 24.dp, end = 24.dp),
                content = "Don't forget to add search keywords so that users can find your work faster."
            )

            TitleText(
                modifier = Modifier.padding(start = 24.dp, top = 24.dp),
                content = "Description",
            )

            DefaultTextField(
                modifier = Modifier
                    .height(120.dp)
                    .padding(start = 24.dp, end = 24.dp)
                    .fillMaxWidth(),
                value = state.description,
                keyboardType = KeyboardType.Text,
                onValueChange = { description ->
                    viewModel.handleIntent(
                        AddQuizContract.AddQuizIntent.ChangeDescription(description)
                    )
                },
                placeholder = stringResource(id = R.string.enter_here)
            )

            DescriptionText(
                modifier = Modifier
                    .padding(start = 24.dp, end = 24.dp),
                content = "Add a description so users can understand what the quiz is about*"
            )

            Button(
                modifier = Modifier
                    .padding(top = 36.dp)
                    .width(190.dp)
                    .align(Alignment.CenterHorizontally),
                onClick = {
                    viewModel.handleIntent(AddQuizContract.AddQuizIntent.LoadSendQuizState)
                }
            ) {
                SFProRoundedText(
                    modifier = Modifier,
                    content = "Next",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
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
































