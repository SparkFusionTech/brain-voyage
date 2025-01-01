package com.sparkfusion.quiz.brainvoyage.domain.model.catalog_progress

import java.time.LocalDateTime

data class CatalogProgressModel(
    val id: Long,
    val playCount: Int,
    val nextTryAt: LocalDateTime
)
