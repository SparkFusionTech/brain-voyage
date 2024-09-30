package com.sparkfusion.quiz.brainvoyage.ui.viewmodel.registration

sealed interface UserExistsState {
    data object Empty : UserExistsState
    data object Loading : UserExistsState
    data object Exists : UserExistsState
    data object NotExists : UserExistsState
    data object Error : UserExistsState
}
