package com.sparkfusion.quiz.brainvoyage.ui.screen.online.selection

import com.sparkfusion.quiz.brainvoyage.domain.model.QuizCatalogModel

data class OnlineQuizCatalogModel(
    val id: Long,
    val name: String,
    val imageUrl: String,
    val startGradientColor: String,
    val endGradientColor: String,
    val isSelected: Boolean = false
) {
    companion object {

        @JvmStatic
        fun mapToOnlineQuizCatalogModel(model: QuizCatalogModel): OnlineQuizCatalogModel = with(model) {
            OnlineQuizCatalogModel(id, name, imageUrl, startGradientColor, endGradientColor)
        }
    }
}



















