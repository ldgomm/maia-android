package com.premierdarkcoffee.sales.maia.root.feature.chat.data.local.entity.product

import com.premierdarkcoffee.sales.maia.root.feature.product.domain.model.product.CreditCard

data class CreditCardEntity(val withoutInterest: Int, val withInterest: Int, val freeMonths: Int) {

    fun toCreditCard(): CreditCard {
        return CreditCard(withoutInterest = withoutInterest, withInterest = withInterest, freeMonths = freeMonths)
    }
}
