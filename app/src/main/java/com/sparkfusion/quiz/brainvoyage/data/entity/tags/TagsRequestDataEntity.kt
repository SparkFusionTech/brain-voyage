package com.sparkfusion.quiz.brainvoyage.data.entity.tags

import com.google.gson.annotations.SerializedName

data class TagsRequestDataEntity(

    @SerializedName("tags")
    val tags: List<String>,

    @SerializedName("quizId")
    val quizId: Long
)
