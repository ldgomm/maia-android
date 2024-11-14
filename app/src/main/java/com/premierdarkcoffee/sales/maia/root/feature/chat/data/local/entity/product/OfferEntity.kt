package com.premierdarkcoffee.sales.hermes.root.feature.chat.data.local.entity.product

import androidx.room.Entity
import com.premierdarkcoffee.sales.maia.root.feature.product.domain.model.product.Offer

data class OfferEntity(val isActive: Boolean, val discount: Int) {

    fun toOffer(): Offer {
        return Offer(isActive = isActive, discount = discount)
    }
}
