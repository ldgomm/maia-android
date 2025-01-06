package com.premierdarkcoffee.sales.maia.root.feature.chat.data.local.entity.product

import com.premierdarkcoffee.sales.maia.root.feature.product.domain.model.product.Size

data class SizeEntity(val width: Double, val height: Double, val depth: Double, val unit: String) {

    fun toSize(): Size {
        return Size(width = width, height = height, depth = depth, unit = unit)
    }
}
