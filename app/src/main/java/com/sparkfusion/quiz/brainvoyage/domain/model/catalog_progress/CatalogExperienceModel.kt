package com.sparkfusion.quiz.brainvoyage.domain.model.catalog_progress

data class CatalogExperienceModel(
    val id: Long,
    val currentXp: Int,
    val levelXp: Int,
    val name: String,
    val level: Int,
    val color: String
)
