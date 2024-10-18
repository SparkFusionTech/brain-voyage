package com.sparkfusion.quiz.brainvoyage.ui.screen.image

import android.content.Context
import android.graphics.Bitmap
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.paging.compose.collectAsLazyPagingItems
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
            modifier = Modifier.padding(paddingValues)
        )

        is ImageSearchingState.PagingSuccess -> {
            val pagingItems = searchingState.data.collectAsLazyPagingItems()
            SuccessImageSearchScreenContent(
                modifier = Modifier.padding(paddingValues),
                pagingItems = pagingItems,
                context = context,
                onHandleErrorState = onHandleErrorState,
                onItemClick = onImageItemClick
            )
        }

        ImageSearchingState.Empty -> EmptyImageSearchScreenContent(
            modifier = Modifier.padding(paddingValues)
        )
    }
}
