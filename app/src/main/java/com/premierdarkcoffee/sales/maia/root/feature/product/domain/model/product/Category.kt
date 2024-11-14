package com.premierdarkcoffee.sales.maia.root.feature.product.domain.model.product

import com.premierdarkcoffee.sales.maia.root.feature.chat.data.local.entity.product.CategoryEntity
import com.premierdarkcoffee.sales.maia.root.feature.product.data.remote.dto.CategoryDto

data class Category(val mi: String, val ni: String, val xi: String) {

    fun toCategoryDto(): CategoryDto {
        return CategoryDto(mi = mi, ni = ni, xi = xi)
    }

    fun toCategoryEntity(): CategoryEntity {
        return CategoryEntity(mi = mi, ni = ni, xi = xi)
    }
}
