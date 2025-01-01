package com.sparkfusion.quiz.brainvoyage.ui.drawer.component

import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.sparkfusion.quiz.brainvoyage.ui.drawer.DrawerContract

@Composable
fun BoxScope.LevelLoadingComponent(
    levelState: DrawerContract.LevelState
) {
    when (levelState) {
        DrawerContract.LevelState.Error -> {}
        DrawerContract.LevelState.Initial -> {}
        is DrawerContract.LevelState.Success -> {
            LevelComponent(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(16.dp),
                catalogExperienceModel = levelState.level
            )
        }
    }
}