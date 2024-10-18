package com.sparkfusion.quiz.brainvoyage.ui.screen.image

import android.graphics.Bitmap
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sparkfusion.quiz.brainvoyage.R
import com.sparkfusion.quiz.brainvoyage.ui.viewmodel.image.ImageSearchContract
import com.sparkfusion.quiz.brainvoyage.ui.viewmodel.image.ImageSearchViewModel
import com.sparkfusion.quiz.brainvoyage.ui.widget.SFProRoundedText
import com.sparkfusion.quiz.brainvoyage.utils.getClearTextFieldContainerColors

@Composable
fun ImageSearchScreen(
    modifier: Modifier = Modifier,
    viewModel: ImageSearchViewModel = hiltViewModel(),
    onImageSelected: (Bitmap, Dp, Dp) -> Unit,
    onNavigateBack: () -> Unit
) {
    val context = LocalContext.current
    val state by viewModel.initialState().collectAsStateWithLifecycle()

    Scaffold(
        modifier = modifier,
        topBar = {
            Row(
                modifier = Modifier
                    .padding(start = 8.dp, end = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                IconButton(onClick = onNavigateBack) {
                    Icon(
                        painter = painterResource(id = R.drawable.arrow_back_icon),
                        contentDescription = stringResource(id = R.string.navigate_back_icon_of_searching_screen_description)
                    )
                }

                TextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = state.query,
                    onValueChange = {
                        viewModel.handleIntent(
                            ImageSearchContract.ImageSearchIntent.ChangeQuery(it)
                        )
                    },
                    placeholder = {
                        SFProRoundedText(content = stringResource(id = R.string.search))
                    },
                    colors = getClearTextFieldContainerColors(),
                    trailingIcon = {
                        IconButton(onClick = { viewModel.handleIntent(ImageSearchContract.ImageSearchIntent.LoadImages) }) {
                            Icon(
                                painter = painterResource(id = R.drawable.search_icon),
                                tint = MaterialTheme.colorScheme.primary,
                                contentDescription = stringResource(id = R.string.search_icon_of_search_button_description)
                            )
                        }
                    }
                )
            }
        }
    ) { paddingValues ->
        ImageScreenContent(
            paddingValues = paddingValues,
            context = context,
            searchingState = state.imageSearchingState,
            onHandleErrorState = { exception ->
                viewModel.handleIntent(ImageSearchContract.ImageSearchIntent.HandleErrorLoading(exception))
            },
            onImageItemClick = {
                onImageSelected(it, 200.dp, 240.dp)
            }
        )
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
private fun ImageSearchScreenPreview() {
    ImageSearchScreen(
        onNavigateBack = {},
        onImageSelected = { _, _, _ ->

        }
    )
}
