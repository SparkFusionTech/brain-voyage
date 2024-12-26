package com.sparkfusion.quiz.brainvoyage.ui.screen.catalog_item

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sparkfusion.quiz.brainvoyage.R
import com.sparkfusion.quiz.brainvoyage.ui.drawer.NavigationDrawer
import com.sparkfusion.quiz.brainvoyage.ui.model.QuizCatalogSerializable
import com.sparkfusion.quiz.brainvoyage.ui.screen.catalog_item.component.CatalogItemTopBar
import com.sparkfusion.quiz.brainvoyage.ui.screen.catalog_item.component.quizzesLoadingHandlerComponent
import com.sparkfusion.quiz.brainvoyage.ui.viewmodel.catalog_item.CatalogItemContract
import com.sparkfusion.quiz.brainvoyage.ui.viewmodel.catalog_item.CatalogItemViewModel
import com.sparkfusion.quiz.brainvoyage.utils.dp.getStatusBarHeightInDp
import com.sparkfusion.quiz.brainvoyage.window.StatusBarHeightOwner
import kotlinx.coroutines.launch

const val QUIZ_ID_KEY = "quiz id key"

@Composable
fun CatalogItemScreen(
    modifier: Modifier = Modifier,
    viewModel: CatalogItemViewModel = hiltViewModel(),
    quizCatalogSerializable: QuizCatalogSerializable,
    onNavigateToQuizAddScreen: (QuizCatalogSerializable) -> Unit,
    onQuizClick: (Long) -> Unit,
    onNavigateToMyQuizzesScreen: () -> Unit
) {
    LaunchedEffect(quizCatalogSerializable.id) {
        viewModel.handleIntent(CatalogItemContract.Intent.LoadQuizzes(quizCatalogSerializable.id))
    }
    val quizzesLoadingState by viewModel.quizLoadingState.collectAsStateWithLifecycle()

    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    NavigationDrawer(
        onMyQuizzesClick = onNavigateToMyQuizzesScreen
    ) { drawerState ->
        Scaffold(
            snackbarHost = {
                SnackbarHost(hostState = snackbarHostState, modifier = Modifier.padding(16.dp))
            },
            topBar = {
                CatalogItemTopBar(
                    modifier = Modifier.padding(
                        top = if (StatusBarHeightOwner.hasCutout) getStatusBarHeightInDp().dp else 0.dp
                    ),
                    title = quizCatalogSerializable.name,
                    onMenuClick = {
                        coroutineScope.launch {
                            if (drawerState.isClosed) drawerState.open() else drawerState.close()
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
            LazyColumn(
                modifier = modifier
                    .paint(
                        painter = painterResource(id = R.drawable.background),
                        contentScale = ContentScale.Crop
                    )
                    .padding(it)
            ) {
                quizzesLoadingHandlerComponent(
                    quizzesLoadingState = quizzesLoadingState,
                    snackbarHostState = snackbarHostState,
                    onItemClick = onQuizClick,
                    onReloadClick = {
                        viewModel.handleIntent(
                            CatalogItemContract.Intent.LoadQuizzes(quizCatalogSerializable.id)
                        )
                    }
                )
            }
        }
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun CatalogItemScreenPreview() {
    CatalogItemScreen(
        quizCatalogSerializable = QuizCatalogSerializable(1, "Music"),
        onNavigateToQuizAddScreen = {},
        onQuizClick = {},
        onNavigateToMyQuizzesScreen = {}
    )
}
