package com.sparkfusion.quiz.brainvoyage.domain.mapper.tag

import com.sparkfusion.quiz.brainvoyage.data.entity.tags.TagDataEntity
import com.sparkfusion.quiz.brainvoyage.domain.model.tag.TagModel
import com.sparkfusion.quiz.brainvoyage.utils.common.MapFactory
import javax.inject.Inject

class TagDataEntityFactory @Inject constructor(): MapFactory<TagDataEntity, TagModel> {

    override suspend fun mapTo(input: TagDataEntity): TagModel {
        return TagModel(input.id, input.name)
    }

    override suspend fun mapFrom(input: TagModel): TagDataEntity {
        return TagDataEntity(input.id, input.name)
    }
}


