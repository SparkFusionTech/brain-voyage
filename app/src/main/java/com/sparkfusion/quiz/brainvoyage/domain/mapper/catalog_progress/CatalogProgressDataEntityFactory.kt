package com.sparkfusion.quiz.brainvoyage.domain.mapper.catalog_progress

import com.sparkfusion.quiz.brainvoyage.data.entity.catalog_progress.CatalogProgressDataEntity
import com.sparkfusion.quiz.brainvoyage.domain.model.catalog_progress.CatalogProgressModel
import com.sparkfusion.quiz.brainvoyage.utils.common.MapFactory
import javax.inject.Inject

class CatalogProgressDataEntityFactory @Inject constructor(
) : MapFactory<CatalogProgressDataEntity, CatalogProgressModel> {

    override suspend fun mapTo(input: CatalogProgressDataEntity): CatalogProgressModel = with(input) {
        CatalogProgressModel(id, playCount, nextTryAt)
    }

    override suspend fun mapFrom(input: CatalogProgressModel): CatalogProgressDataEntity = with(input) {
        CatalogProgressDataEntity(id, playCount, nextTryAt)
    }
}