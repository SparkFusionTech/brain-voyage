package com.sparkfusion.quiz.brainvoyage.ui.viewmodel.online

import androidx.lifecycle.ViewModel
import com.sparkfusion.quiz.brainvoyage.R
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class OnlineGamesViewModel @Inject constructor(
): ViewModel() {

    private val _games = MutableStateFlow<List<GameModel>>(emptyList())
    val games: StateFlow<List<GameModel>> = _games.asStateFlow()

    private fun provideGamesList() {
        val gameModels = listOf(
            GameModel(R.string.game_1vs1, R.drawable.game_1vs1_icon)
        )
        _games.update { gameModels }
    }

    init {
        provideGamesList()
    }
}

























