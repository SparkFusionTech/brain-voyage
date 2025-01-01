package com.sparkfusion.quiz.brainvoyage.data.entity.catalog_progress

import com.google.gson.annotations.JsonAdapter
import com.google.gson.annotations.SerializedName
import com.sparkfusion.quiz.brainvoyage.data.entity.LocalDateTimeAdapter
import java.time.LocalDateTime

data class CatalogProgressDataEntity(

    @SerializedName("id")
    val id: Long,

    @SerializedName("playCount")
    val playCount: Int,

    @SerializedName("nextTryAt")
    @JsonAdapter(LocalDateTimeAdapter::class)
    val nextTryAt: LocalDateTime
)
