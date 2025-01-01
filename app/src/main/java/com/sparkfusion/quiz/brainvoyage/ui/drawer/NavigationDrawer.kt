package com.sparkfusion.quiz.brainvoyage.ui.drawer

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sparkfusion.quiz.brainvoyage.R
import com.sparkfusion.quiz.brainvoyage.ui.drawer.component.AccountIconComponent
import com.sparkfusion.quiz.brainvoyage.ui.drawer.component.AccountNameComponent
import com.sparkfusion.quiz.brainvoyage.ui.drawer.component.LevelLoadingComponent
import com.sparkfusion.quiz.brainvoyage.ui.theme.drawerContainerDarkColor

@Composable
fun NavigationDrawer(
    modifier: Modifier = Modifier,
    viewModel: DrawerViewModel,
    onMyQuizzesClick: () -> Unit,
    content: @Composable (drawerState: DrawerState) -> Unit
) {
    val state by viewModel.initialState.collectAsStateWithLifecycle()
    val levelState by viewModel.levelState.collectAsStateWithLifecycle()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

    LaunchedEffect(drawerState) {
        snapshotFlow { drawerState.isOpen }.collect { isOpen ->
            if (isOpen) {
                viewModel.handleIntent(DrawerContract.DrawerIntent.ReloadUserInfo)
            }
        }
    }

    ModalNavigationDrawer(
        modifier = modifier,
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(
                modifier = Modifier.width(330.dp),
                drawerContainerColor = drawerContainerDarkColor
            ) {
                Box(modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 48.dp)) {
                    LevelLoadingComponent(levelState = levelState)

                    Column {
                        Spacer(modifier = Modifier.height(56.dp))

                        AccountIconComponent(
                            modifier = Modifier
                                .padding(start = 12.dp, top = 24.dp)
                                .size(96.dp)
                                .clip(CircleShape),
                            accountInfoState = state.accountInfoState
                        )

                        AccountNameComponent(
                            modifier = Modifier.padding(start = 12.dp, top = 12.dp, bottom = 24.dp),
                            state = state.accountInfoState
                        )
                    }
                }

                DrawerItemComponent(
                    label = "My profile",
                    icon = painterResource(id = R.drawable.round_account),
                    onItemClick = {}
                )

                DrawerItemComponent(
                    label = "Settings",
                    icon = painterResource(id = R.drawable.round_settings),
                    onItemClick = {}
                )

                DrawerItemComponent(
                    label = "My quizzes",
                    icon = painterResource(id = R.drawable.round_settings),
                    onItemClick = onMyQuizzesClick
                )
            }
        }
    ) {
        content(drawerState)
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun NavigationDrawerPreview() {
    NavigationDrawer(
        viewModel = hiltViewModel(),
        content = {},
        onMyQuizzesClick = {}
    )
}
























