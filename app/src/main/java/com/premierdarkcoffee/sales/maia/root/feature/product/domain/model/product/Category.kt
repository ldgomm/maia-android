package com.premierdarkcoffee.sales.maia.root.feature.product.domain.model.product

import com.premierdarkcoffee.sales.maia.root.feature.chat.data.local.entity.product.CategoryEntity
import com.premierdarkcoffee.sales.maia.root.feature.product.data.remote.dto.CategoryDto

data class Category(val group: String, val domain: String, val subclass: String) {

    fun toCategoryDto(): CategoryDto {
        return CategoryDto(group = group, domain = domain, subclass = subclass)
    }

    fun toCategoryEntity(): CategoryEntity {
        return CategoryEntity(group = group, domain = domain, subclass = subclass)
    }
}
