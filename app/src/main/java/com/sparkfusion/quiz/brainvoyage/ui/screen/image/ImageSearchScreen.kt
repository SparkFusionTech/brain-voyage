package com.sparkfusion.quiz.brainvoyage.ui.screen.image

import android.graphics.Bitmap
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sparkfusion.quiz.brainvoyage.R
import com.sparkfusion.quiz.brainvoyage.ui.screen.image.component.ImageScreenContent
import com.sparkfusion.quiz.brainvoyage.ui.theme.settingsBackgroundDarkColor
import com.sparkfusion.quiz.brainvoyage.ui.viewmodel.image.ImageSearchContract
import com.sparkfusion.quiz.brainvoyage.ui.viewmodel.image.ImageSearchViewModel
import com.sparkfusion.quiz.brainvoyage.ui.widget.SFProRoundedText
import com.sparkfusion.quiz.brainvoyage.utils.dp.getStatusBarHeightInDp
import com.sparkfusion.quiz.brainvoyage.window.StatusBarHeightOwner

@Composable
fun ImageSearchScreen(
    modifier: Modifier = Modifier,
    viewModel: ImageSearchViewModel = hiltViewModel(),
    onImageSelected: (Bitmap) -> Unit,
    onNavigateBack: () -> Unit
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val context = LocalContext.current
    val state by viewModel.initialState().collectAsStateWithLifecycle()

    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = settingsBackgroundDarkColor,
        topBar = {
            Row(
                modifier = Modifier
                    .background(settingsBackgroundDarkColor)
                    .padding(
                        start = 8.dp,
                        end = 8.dp,
                        top = if (StatusBarHeightOwner.hasCutout) getStatusBarHeightInDp().dp else 0.dp
                    ),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                IconButton(onClick = onNavigateBack) {
                    Icon(
                        painter = painterResource(id = R.drawable.arrow_back_icon),
                        contentDescription = stringResource(id = R.string.navigate_back_icon_of_searching_screen_description),
                        tint = Color.White
                    )
                }

                TextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = state.query,
                    maxLines = 1,
                    singleLine = true,
                    onValueChange = {
                        viewModel.handleIntent(
                            ImageSearchContract.ImageSearchIntent.ChangeQuery(it)
                        )
                    },
                    placeholder = {
                        SFProRoundedText(content = stringResource(id = R.string.search), color = Color.White)
                    },
                    colors = TextFieldDefaults.colors(
                        unfocusedContainerColor = Color.Transparent,
                        focusedContainerColor = Color.Transparent,
                        disabledContainerColor = Color.Transparent,
                        errorContainerColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        errorIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        focusedTextColor = Color.White,
                        errorTextColor = Color.White,
                        unfocusedTextColor = Color.White,
                        disabledTextColor = Color.White
                    ),
                    trailingIcon = {
                        IconButton(
                            onClick = {
                                viewModel.handleIntent(ImageSearchContract.ImageSearchIntent.LoadImages)
                                keyboardController?.hide()
                            }
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.search_icon),
                                tint = Color.White,
                                contentDescription = stringResource(id = R.string.search_icon_of_search_button_description)
                            )
                        }
                    },
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                    keyboardActions = KeyboardActions(
                        onSearch = {
                            viewModel.handleIntent(ImageSearchContract.ImageSearchIntent.LoadImages)
                            keyboardController?.hide()
                        }
                    )
                )
            }
        }
    ) { paddingValues ->
        ImageScreenContent(
            paddingValues = paddingValues,
            context = context,
            searchingState = state.imageSearchingState,
            onHandleErrorState = { exception ->
                viewModel.handleIntent(
                    ImageSearchContract.ImageSearchIntent.HandleErrorLoading(
                        exception
                    )
                )
            },
            onImageItemClick = {
                onImageSelected(it)
            }
        )
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
private fun ImageSearchScreenPreview() {
    ImageSearchScreen(
        onNavigateBack = {},
        onImageSelected = { _ -> }
    )
}
