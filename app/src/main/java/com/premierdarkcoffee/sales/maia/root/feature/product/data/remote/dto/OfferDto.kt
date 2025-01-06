package com.premierdarkcoffee.sales.maia.root.feature.product.data.remote.dto

import com.premierdarkcoffee.sales.maia.root.feature.product.domain.model.product.Offer
import kotlinx.serialization.Serializable

@Serializable
data class OfferDto(val isActive: Boolean, val discount: Int) {

    fun toOffer(): Offer {
        return Offer(isActive = isActive, discount= discount)
    }
}
