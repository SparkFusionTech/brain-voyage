package com.sparkfusion.quiz.brainvoyage.domain.mapper.tag

import com.sparkfusion.quiz.brainvoyage.data.entity.tags.TagDataEntity
import com.sparkfusion.quiz.brainvoyage.domain.model.tag.TagModel
import com.sparkfusion.quiz.brainvoyage.utils.common.MapFactory
import javax.inject.Inject

class TagsListFactory @Inject constructor(
    private val tagDataEntityFactory: TagDataEntityFactory
) : MapFactory<List<TagDataEntity>, List<TagModel>> {

    override suspend fun mapTo(input: List<TagDataEntity>): List<TagModel> {
        return input.map {
            tagDataEntityFactory.mapTo(it)
        }
    }

    override suspend fun mapFrom(input: List<TagModel>): List<TagDataEntity> {
        return input.map {
            tagDataEntityFactory.mapFrom(it)
        }
    }
}