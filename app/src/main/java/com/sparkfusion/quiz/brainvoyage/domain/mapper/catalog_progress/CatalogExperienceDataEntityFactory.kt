package com.sparkfusion.quiz.brainvoyage.domain.mapper.catalog_progress

import com.sparkfusion.quiz.brainvoyage.data.entity.catalog_progress.CatalogExperienceDataEntity
import com.sparkfusion.quiz.brainvoyage.domain.model.catalog_progress.CatalogExperienceModel
import com.sparkfusion.quiz.brainvoyage.utils.common.MapFactory
import javax.inject.Inject

class CatalogExperienceDataEntityFactory @Inject constructor(
) : MapFactory<CatalogExperienceDataEntity, CatalogExperienceModel> {

    override suspend fun mapTo(input: CatalogExperienceDataEntity): CatalogExperienceModel = with(input) {
        CatalogExperienceModel(id, currentXp, levelXp, name, level, color)
    }

    override suspend fun mapFrom(input: CatalogExperienceModel): CatalogExperienceDataEntity = with(input) {
        CatalogExperienceDataEntity(id, currentXp, levelXp, name, level, color)
    }
}