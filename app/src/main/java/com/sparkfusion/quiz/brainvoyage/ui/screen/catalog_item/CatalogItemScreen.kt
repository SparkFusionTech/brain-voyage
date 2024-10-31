package com.sparkfusion.quiz.brainvoyage.ui.screen.catalog_item

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.LargeFloatingActionButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.sparkfusion.quiz.brainvoyage.R
import com.sparkfusion.quiz.brainvoyage.ui.drawer.NavigationDrawer
import com.sparkfusion.quiz.brainvoyage.ui.model.QuizCatalogSerializable
import kotlinx.coroutines.launch

@Composable
fun CatalogItemScreen(
    modifier: Modifier = Modifier,
    quizCatalogSerializable: QuizCatalogSerializable,
    onNavigateToQuizAddScreen: (QuizCatalogSerializable) -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    NavigationDrawer { drawerState ->
        Scaffold(
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
            Box(modifier = modifier.padding(it)) {}
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
