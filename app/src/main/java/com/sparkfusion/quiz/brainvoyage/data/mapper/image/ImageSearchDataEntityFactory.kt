package com.sparkfusion.quiz.brainvoyage.data.mapper.image

import com.sparkfusion.quiz.brainvoyage.data.entity.ImageSearchDataEntity
import com.sparkfusion.quiz.brainvoyage.domain.model.ImageSearchModel
import com.sparkfusion.quiz.brainvoyage.utils.common.MapFactory
import javax.inject.Inject

class ImageSearchDataEntityFactory @Inject constructor() :
    MapFactory<ImageSearchDataEntity, ImageSearchModel> {

    override suspend fun mapTo(input: ImageSearchDataEntity): ImageSearchModel = with(input) {
        ImageSearchModel(url, width, height)
    }

    override suspend fun mapFrom(input: ImageSearchModel): ImageSearchDataEntity = with(input) {
        ImageSearchDataEntity(url, width, height)
    }
}
