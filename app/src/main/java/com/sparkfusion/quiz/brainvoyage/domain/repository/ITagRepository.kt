package com.sparkfusion.quiz.brainvoyage.domain.repository

import com.sparkfusion.quiz.brainvoyage.utils.common.Answer

interface ITagRepository {

    suspend fun createTags(tags: List<String>, quizId: Long): Answer<Int>
}
