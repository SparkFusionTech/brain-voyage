package com.sparkfusion.quiz.brainvoyage.data.entity.catalog_progress

import com.google.gson.annotations.SerializedName

data class CatalogExperienceDataEntity(

    @SerializedName("id")
    val id: Long,

    @SerializedName("currentXp")
    val currentXp: Int,

    @SerializedName("levelXp")
    val levelXp: Int,

    @SerializedName("name")
    val name: String,

    @SerializedName("level")
    val level: Int,

    @SerializedName("color")
    val color: String
)
