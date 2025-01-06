package com.premierdarkcoffee.sales.maia.root.feature.product.domain.model.product

import com.premierdarkcoffee.sales.maia.root.feature.chat.data.local.entity.product.SizeEntity
import com.premierdarkcoffee.sales.maia.root.feature.product.data.remote.dto.SizeDto

data class Size(val width: Double, val height: Double, val depth: Double, val unit: String) {

    fun toSizeDto(): SizeDto {
        return SizeDto(width = width, height = height, depth = depth, unit = unit)
    }

    fun toSizeEntity(): SizeEntity {
        return SizeEntity(width = width, height = height, depth = depth, unit = unit)
    }
}
