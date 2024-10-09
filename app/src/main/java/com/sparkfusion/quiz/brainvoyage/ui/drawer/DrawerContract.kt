package com.sparkfusion.quiz.brainvoyage.ui.drawer

import com.sparkfusion.quiz.brainvoyage.utils.common.Intent
import com.sparkfusion.quiz.brainvoyage.utils.common.UIState

interface DrawerContract {

    sealed interface DrawerIntent : Intent {
        data object ReloadUserInfo : DrawerIntent
    }

    data class DrawerState(
        val accountInfoState: DrawerReadingState = DrawerReadingState.Loading
    ) : UIState
}
