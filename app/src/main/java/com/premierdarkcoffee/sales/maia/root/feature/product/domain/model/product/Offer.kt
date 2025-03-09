package com.premierdarkcoffee.sales.maia.root.feature.product.domain.model.product

import com.premierdarkcoffee.sales.maia.root.feature.chat.data.local.entity.product.OfferEntity
import com.premierdarkcoffee.sales.maia.root.feature.product.data.remote.dto.OfferDto

data class Offer(val isActive: Boolean, val discount: Int) {

    fun toOfferDto(): OfferDto {
        return OfferDto(isActive = isActive, discount = discount)
    }

    fun toOfferEntity(): OfferEntity {
        return OfferEntity(isActive = isActive, discount = discount)
    }
}
