package com.premierdarkcoffee.sales.maia.root.feature.product.data.remote.dto

import com.premierdarkcoffee.sales.maia.root.feature.product.domain.model.product.CreditCard
import kotlinx.serialization.Serializable

@Serializable
data class CreditCardDto(val withoutInterest: Int, val withInterest: Int, val freeMonths: Int) {

    fun toCreditCard(): CreditCard {
        return CreditCard(withoutInterest = withoutInterest, withInterest = withInterest, freeMonths = freeMonths)
    }
}
