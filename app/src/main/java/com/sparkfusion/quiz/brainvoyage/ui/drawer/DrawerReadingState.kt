package com.sparkfusion.quiz.brainvoyage.ui.drawer

import com.sparkfusion.quiz.brainvoyage.domain.model.AccountInfoModel

sealed interface DrawerReadingState {
    data object Loading : DrawerReadingState
    data object NetworkError : DrawerReadingState
    data object Error : DrawerReadingState

    data class Success(val accountInfoModel: AccountInfoModel) : DrawerReadingState
}
