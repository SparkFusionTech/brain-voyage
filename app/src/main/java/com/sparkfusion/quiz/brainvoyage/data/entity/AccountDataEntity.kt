package com.sparkfusion.quiz.brainvoyage.data.entity

import com.google.gson.annotations.JsonAdapter
import java.time.LocalDateTime

data class AccountDataEntity(
    val id: Long,
    val email: String,
    val password: String,
    val iconUrl: String,
    val name: String,
    @JsonAdapter(LocalDateTimeAdapter::class) val createdAt: LocalDateTime
)
