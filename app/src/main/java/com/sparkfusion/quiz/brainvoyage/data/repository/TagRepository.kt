package com.sparkfusion.quiz.brainvoyage.data.repository

import com.sparkfusion.quiz.brainvoyage.data.common.ApiListResponseHandler
import com.sparkfusion.quiz.brainvoyage.data.common.ApiResponseHandler
import com.sparkfusion.quiz.brainvoyage.data.common.handleExceptionCode
import com.sparkfusion.quiz.brainvoyage.data.common.safeApiCall
import com.sparkfusion.quiz.brainvoyage.data.datasource.TagApiService
import com.sparkfusion.quiz.brainvoyage.data.entity.tags.TagsRequestDataEntity
import com.sparkfusion.quiz.brainvoyage.domain.mapper.tag.TagsListFactory
import com.sparkfusion.quiz.brainvoyage.domain.model.tag.TagModel
import com.sparkfusion.quiz.brainvoyage.domain.repository.ITagRepository
import com.sparkfusion.quiz.brainvoyage.utils.common.Answer
import com.sparkfusion.quiz.brainvoyage.utils.dispatchers.IODispatcher
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TagRepository @Inject constructor(
    @IODispatcher private val ioDispatcher: CoroutineDispatcher,
    private val tagApiService: TagApiService,
    private val tagsListFactory: TagsListFactory
) : ITagRepository {

    override suspend fun createTags(tags: List<String>, quizId: Long): Answer<Int> =
        safeApiCall(ioDispatcher) {
            ApiResponseHandler(
                tagApiService.createTags(TagsRequestDataEntity(tags, quizId)),
                ::handleExceptionCode
            ).handleFetchedData()
        }

    override suspend fun readTagsByQuizId(quizId: Long): Answer<List<TagModel>> =
        safeApiCall(ioDispatcher) {
            ApiListResponseHandler(
                tagApiService.readTagsByQuizId(quizId),
                ::handleExceptionCode
            )
                .handleFetchedData()
                .suspendMap(tagsListFactory::mapTo)
        }
}




















