package com.sparkfusion.quiz.brainvoyage.ui.screen.image.component

import android.content.Context
import android.graphics.Bitmap
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.paging.compose.collectAsLazyPagingItems
import com.sparkfusion.quiz.brainvoyage.ui.theme.settingsBackgroundDarkColor
import com.sparkfusion.quiz.brainvoyage.ui.theme.settingsBackgroundLightColor
import com.sparkfusion.quiz.brainvoyage.ui.viewmodel.image.ImageSearchingState

@Composable
fun ImageScreenContent(
    paddingValues: PaddingValues,
    context: Context,
    onImageItemClick: (Bitmap) -> Unit,
    searchingState: ImageSearchingState,
    onHandleErrorState: (Throwable) -> Unit
) {
    when (searchingState) {
        is ImageSearchingState.Loading -> LoadingImageSearchScreenContent(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.linearGradient(
                        listOf(
                            settingsBackgroundLightColor,
                            settingsBackgroundDarkColor
                        )
                    )
                )
                .padding(paddingValues)
        )

        is ImageSearchingState.PagingSuccess -> {
            val pagingItems = searchingState.data.collectAsLazyPagingItems()
            SuccessImageSearchScreenContent(
                modifier = Modifier
                    .background(
                        Brush.linearGradient(
                            listOf(
                                settingsBackgroundLightColor,
                                settingsBackgroundDarkColor
                            )
                        )
                    )
                    .padding(paddingValues),
                pagingItems = pagingItems,
                context = context,
                onHandleErrorState = onHandleErrorState,
                onItemClick = onImageItemClick
            )
        }

        ImageSearchingState.Empty -> EmptyImageSearchScreenContent(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.linearGradient(
                        listOf(
                            settingsBackgroundLightColor,
                            settingsBackgroundDarkColor
                        )
                    )
                )
                .padding(paddingValues)
        )
    }
}
