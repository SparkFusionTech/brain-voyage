package com.sparkfusion.quiz.brainvoyage.data.entity.tags

import com.google.gson.annotations.SerializedName

data class TagDataEntity(

    @SerializedName("id")
    val id: Long,

    @SerializedName("name")
    val name: String
)
