package com.sparkfusion.quiz.brainvoyage.ui.screen.add_quiz

import android.graphics.Bitmap
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import com.sparkfusion.quiz.brainvoyage.ui.screen.add_quiz.component.AddQuizTopComponent
import com.sparkfusion.quiz.brainvoyage.ui.viewmodel.add_quiz.AddQuizContract
import com.sparkfusion.quiz.brainvoyage.ui.viewmodel.add_quiz.AddQuizViewModel
import com.sparkfusion.quiz.brainvoyage.ui.widget.SFProRoundedText
import com.sparkfusion.quiz.brainvoyage.ui.widget.dialog.SelectImageDialog
import com.sparkfusion.quiz.brainvoyage.ui.widget.text.DefaultTextField
import com.sparkfusion.quiz.brainvoyage.ui.widget.text.DescriptionText
import com.sparkfusion.quiz.brainvoyage.ui.widget.text.TitleText
import com.sparkfusion.quiz.brainvoyage.utils.descriptionColor

@Composable
fun AddQuizScreen(
    modifier: Modifier = Modifier,
    viewModel: AddQuizViewModel = hiltViewModel(),
    onBackClick: () -> Unit,
    onNextClick: () -> Unit,
    onSearchImageScreenNavigate: () -> Unit,
    onImageCropNavigate: (Bitmap) -> Unit,
    getCroppedImageBitmap: () -> Bitmap?
) {
    viewModel.handleIntent(AddQuizContract.AddQuizIntent.ChangeIcon(getCroppedImageBitmap()))
    val state by viewModel.state.collectAsStateWithLifecycle()
    val changeDialogVisibility = { v: Boolean ->
        viewModel.handleIntent(AddQuizContract.AddQuizIntent.ChangeImageSelectionDialogVisibility(v))
    }

    val context = LocalContext.current
    val galleryLauncher = rememberLauncherForImageCropping(
        context = context,
        onImageCropNavigate
    )

    SelectImageDialog(
        show = state.showImageSelectionDialog,
        onSearchNavigate = {
            onSearchImageScreenNavigate()
            changeDialogVisibility(false)
        },
        onGalleryNavigate = {
            galleryLauncher.launch("image/*")
            changeDialogVisibility(false)
        },
        onDismiss = {
            changeDialogVisibility(false)
        }
    )

    Column(
        modifier = modifier.fillMaxSize()
    ) {
        AddQuizTopComponent(
            modifier = Modifier,
            onBackClick = onBackClick
        )

        AsyncImage(
            modifier = Modifier
                .clickable { changeDialogVisibility(true) }
                .align(Alignment.CenterHorizontally)
                .padding(top = 40.dp)
                .size(156.dp),
            model = if (state.bitmap == null) R.drawable.select_image_icon else state.bitmap,
            contentDescription = null
        )

        Row(
            modifier = Modifier.padding(start = 24.dp, end = 24.dp, top = 24.dp)
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
            onValueChange = { viewModel.handleIntent(AddQuizContract.AddQuizIntent.ChangeTitle(it)) },
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
            onValueChange = {
                viewModel.handleIntent(
                    AddQuizContract.AddQuizIntent.ChangeDescription(
                        it
                    )
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
            onClick = onNextClick
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

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun AddQuizScreenPreview() {
    AddQuizScreen(
        onBackClick = {},
        onNextClick = {},
        onSearchImageScreenNavigate = {},
        onImageCropNavigate = {},
        getCroppedImageBitmap = { null }
    )
}
































