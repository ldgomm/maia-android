package com.premierdarkcoffee.sales.maia.root.feature.product.domain.model.product

import com.premierdarkcoffee.sales.maia.root.feature.chat.data.local.entity.product.WeightEntity
import com.premierdarkcoffee.sales.maia.root.feature.product.data.remote.dto.WeightDto

data class Weight(val weight: Double, val unit: String) {

    fun toWeightDto(): WeightDto {
        return WeightDto(weight = weight, unit = unit)
    }

    fun toWeightEntity(): WeightEntity {
        return WeightEntity(weight = weight, unit = unit)
    }
}
