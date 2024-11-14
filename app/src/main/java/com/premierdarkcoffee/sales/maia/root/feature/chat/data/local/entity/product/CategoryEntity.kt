package com.premierdarkcoffee.sales.maia.root.feature.chat.data.local.entity.product

import com.premierdarkcoffee.sales.maia.root.feature.product.domain.model.product.Category

data class CategoryEntity(val mi: String, val ni: String, val xi: String) {

    fun toCategory(): Category {
        return Category(mi = mi, ni = ni, xi = xi)
    }
}