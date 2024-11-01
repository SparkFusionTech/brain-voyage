package com.sparkfusion.quiz.brainvoyage.ui.screen.image.component

import android.content.Context
import android.graphics.Bitmap
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.sparkfusion.quiz.brainvoyage.R
import com.sparkfusion.quiz.brainvoyage.domain.model.ImageSearchModel

@Composable
fun SuccessImageSearchScreenContent(
    modifier: Modifier = Modifier,
    pagingItems: LazyPagingItems<ImageSearchModel>,
    context: Context,
    onItemClick: (Bitmap) -> Unit,
    onHandleErrorState: (Throwable) -> Unit
) {
    LazyVerticalStaggeredGrid(
        modifier = modifier.padding(horizontal = 4.dp),
        columns = StaggeredGridCells.Fixed(2),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalItemSpacing = 4.dp
    ) {
        item {
            ErrorListener(
                state = pagingItems.loadState,
                context = context,
                onHandleErrorState = onHandleErrorState
            )
        }

        items(pagingItems.itemCount) { index ->
            pagingItems[index]?.let { model ->
                ImageItemComponent(
                    context = context,
                    model = model,
                    onClick = onItemClick
                )
            }
        }

        if (pagingItems.loadState.append is LoadState.Loading) {
            item {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .padding(top = 20.dp)
                        .height(60.dp)
                        .fillMaxWidth()
                ) {
                    CircularProgressIndicator()
                }
            }
        }
    }
}

@Composable
private fun ErrorListener(
    state: CombinedLoadStates,
    context: Context,
    onHandleErrorState: (Throwable) -> Unit
) {
    LaunchedEffect(state) {
        snapshotFlow { state }
            .collect { loadState ->
                if (loadState.append is LoadState.Error) {
                    Toast.makeText(
                        context, R.string.error, Toast.LENGTH_SHORT
                    ).show()
                }

                if (loadState.refresh is LoadState.Error) {
                    onHandleErrorState((loadState.refresh as LoadState.Error).error)
                }
            }
    }
}
