package com.sparkfusion.quiz.brainvoyage.ui.screen.catalog_item

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.LargeFloatingActionButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sparkfusion.quiz.brainvoyage.R
import com.sparkfusion.quiz.brainvoyage.ui.drawer.NavigationDrawer
import com.sparkfusion.quiz.brainvoyage.ui.model.QuizCatalogSerializable
import com.sparkfusion.quiz.brainvoyage.ui.viewmodel.catalog_item.CatalogItemContract
import com.sparkfusion.quiz.brainvoyage.ui.viewmodel.catalog_item.CatalogItemViewModel
import kotlinx.coroutines.launch

@Composable
fun CatalogItemScreen(
    modifier: Modifier = Modifier,
    viewModel: CatalogItemViewModel = hiltViewModel(),
    quizCatalogSerializable: QuizCatalogSerializable,
    onNavigateToQuizAddScreen: (QuizCatalogSerializable) -> Unit
) {
    viewModel.handleIntent(CatalogItemContract.Intent.LoadQuizzes(quizCatalogSerializable.id))
    val quizzesLoadingState by viewModel.quizLoadingState.collectAsStateWithLifecycle()

    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    NavigationDrawer { drawerState ->
        Scaffold(
            snackbarHost = {
                SnackbarHost(hostState = snackbarHostState, modifier = Modifier.padding(16.dp))
            },
            topBar = {
                CatalogItemTopBar(
                    title = quizCatalogSerializable.name,
                    onMenuClick = {
                        coroutineScope.launch {
                            drawerState.apply {
                                if (isClosed) open() else close()
                            }
                        }
                    }
                )
            },
            floatingActionButton = {
                LargeFloatingActionButton(
                    onClick = { onNavigateToQuizAddScreen(quizCatalogSerializable) }
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.plus_icon),
                        contentDescription = stringResource(id = R.string.floating_add_quiz_button_icon_description)
                    )
                }
            },
            floatingActionButtonPosition = FabPosition.End
        ) {
            LazyColumn(modifier = modifier.padding(it)) {
                when (quizzesLoadingState) {
                    CatalogItemContract.QuizLoadingState.Error -> {
                        item {
                            LaunchedEffect(quizzesLoadingState) {
                                snackbarHostState.showSnackbar("Unknown error")
                            }
                            ReloadQuizzesComponent {
                                viewModel.handleIntent(CatalogItemContract.Intent.LoadQuizzes(quizCatalogSerializable.id))
                            }
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
                            LaunchedEffect(quizzesLoadingState) {
                                snackbarHostState.showSnackbar("Network error")
                            }
                            ReloadQuizzesComponent {
                                viewModel.handleIntent(CatalogItemContract.Intent.LoadQuizzes(quizCatalogSerializable.id))
                            }
                        }
                    }

                    is CatalogItemContract.QuizLoadingState.Success -> {
                        val state = (quizzesLoadingState as CatalogItemContract.QuizLoadingState.Success)
                        items(state.data.size) { index ->
                            QuizItemComponent(
                                quiz = state.data[index],
                                onItemClick = {

                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun CatalogItemScreenPreview() {
    CatalogItemScreen(
        quizCatalogSerializable = QuizCatalogSerializable(1, "Music"),
        onNavigateToQuizAddScreen = {}
    )
}
