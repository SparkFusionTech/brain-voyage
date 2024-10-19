package com.sparkfusion.quiz.brainvoyage.ui.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed interface Destination {

    @Serializable
    data object LoginDestination : Destination

    @Serializable
    data object RegistrationDestination : Destination

    @Serializable
    data object CatalogDestination : Destination

    @Serializable
    data object CatalogItemDestination : Destination

    @Serializable
    data object QuizItemDestination : Destination

    @Serializable
    data object ImageCropDestination : Destination

    @Serializable
    data object ImageSearchDestination : Destination

    @Serializable
    data object AddQuizDestination : Destination

    @Serializable
    data object AddQuizWithQuestionsDestination : Destination

    companion object {

        @JvmStatic
        fun getDestinationRoute(destination: Destination): String {
            return destination.javaClass.name.replace("$", ".")
        }
    }
}
