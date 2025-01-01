package com.sparkfusion.quiz.brainvoyage.ui.drawer

import com.sparkfusion.quiz.brainvoyage.domain.model.catalog_progress.CatalogExperienceModel
import com.sparkfusion.quiz.brainvoyage.utils.common.Intent
import com.sparkfusion.quiz.brainvoyage.utils.common.UIState

interface DrawerContract {

    sealed interface DrawerIntent : Intent {
        data object ReloadUserInfo : DrawerIntent
        data object ReloadCatalogLevel : DrawerIntent
    }

    data class DrawerState(
        val accountInfoState: DrawerReadingState = DrawerReadingState.Loading
    ) : UIState

    sealed interface LevelState {
        data object Error : LevelState
        data object Initial : LevelState
        data class Success(val level: CatalogExperienceModel) : LevelState
    }
}
