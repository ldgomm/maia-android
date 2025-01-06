package com.premierdarkcoffee.sales.maia.root.feature.product.domain.model.product

import com.premierdarkcoffee.sales.maia.root.feature.chat.data.local.entity.product.CreditCardEntity
import com.premierdarkcoffee.sales.maia.root.feature.product.data.remote.dto.CreditCardDto

data class CreditCard(
    val withoutInterest: Int,
    val withInterest: Int,
    val freeMonths: Int
) {

    fun toCreditCardDto(): CreditCardDto {
        return CreditCardDto(withoutInterest = withoutInterest, withInterest = withInterest, freeMonths = freeMonths)
    }

    fun toCreditCardEntity(): CreditCardEntity {
        return CreditCardEntity(withoutInterest = withoutInterest, withInterest = withInterest, freeMonths = freeMonths)
    }
}
