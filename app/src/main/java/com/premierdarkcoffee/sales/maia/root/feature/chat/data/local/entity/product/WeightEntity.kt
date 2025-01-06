package com.premierdarkcoffee.sales.maia.root.feature.chat.data.local.entity.product

import com.premierdarkcoffee.sales.maia.root.feature.product.domain.model.product.Weight

data class WeightEntity(val weight: Double, val unit: String) {

    fun toWeight(): Weight {
        return Weight(weight = weight, unit = unit)
    }
}
