package com.sparkfusion.quiz.brainvoyage.domain.model

import java.time.LocalDateTime

data class AccountModel(
    val id: Long,
    val email: String,
    val password: String,
    val iconUrl: String,
    val name: String,
    val createdAt: LocalDateTime
)
