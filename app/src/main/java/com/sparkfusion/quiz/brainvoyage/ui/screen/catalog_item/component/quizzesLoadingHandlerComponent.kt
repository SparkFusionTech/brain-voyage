package com.sparkfusion.quiz.brainvoyage.ui.screen.catalog_item.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.sparkfusion.quiz.brainvoyage.R
import com.sparkfusion.quiz.brainvoyage.ui.viewmodel.catalog_item.CatalogItemContract
import com.sparkfusion.quiz.brainvoyage.ui.widget.reload.ReloadComponent

fun LazyListScope.quizzesLoadingHandlerComponent(
    quizzesLoadingState: CatalogItemContract.QuizLoadingState,
    snackbarHostState: SnackbarHostState,
    onReloadClick: () -> Unit,
    onItemClick: (Long) -> Unit
) {
    when (quizzesLoadingState) {
        CatalogItemContract.QuizLoadingState.Error -> {
            item {
                val errorMessage = stringResource(id = R.string.error)
                LaunchedEffect(Unit) {
                    snackbarHostState.showSnackbar(errorMessage)
                }
                ReloadComponent(onReloadClick = onReloadClick) {}
            }
        }

        CatalogItemContract.QuizLoadingState.Loading -> {
            item {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
        }

        CatalogItemContract.QuizLoadingState.NetworkError -> {
            item {
                val networkErrorMessage = stringResource(id = R.string.network_error)
                LaunchedEffect(Unit) {
                    snackbarHostState.showSnackbar(networkErrorMessage)
                }
                ReloadComponent(onReloadClick = onReloadClick) {}
            }
        }

        is CatalogItemContract.QuizLoadingState.Success -> {
            items(quizzesLoadingState.data, key = { it.id }) {
                QuizItemComponent(quiz = it, onItemClick = onItemClick)
            }
        }
    }
}
