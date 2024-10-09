package com.sparkfusion.quiz.brainvoyage.ui.drawer

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
import androidx.compose.runtime.getValue
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

@Composable
fun NavigationDrawer(
    modifier: Modifier = Modifier,
    viewModel: DrawerViewModel = hiltViewModel(),
    content: @Composable (drawerState: DrawerState) -> Unit
) {
    val state by viewModel.initialState().collectAsStateWithLifecycle()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

    ModalNavigationDrawer(
        modifier = modifier,
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(modifier = Modifier.width(330.dp)) {
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

                DrawerItemComponent(
                    label = "My profile",
                    icon = painterResource(id = R.drawable.round_account)
                )

                DrawerItemComponent(
                    label = "Settings",
                    icon = painterResource(id = R.drawable.round_settings)
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
        content = {}
    )
}
























