package com.premierdarkcoffee.sales.maia.root.feature.chat.data.local.entity.product

import com.premierdarkcoffee.sales.hermes.root.feature.chat.data.local.entity.product.OfferEntity
import com.premierdarkcoffee.sales.maia.root.feature.product.domain.model.product.Price

data class PriceEntity(val amount: Double,
                       val currency: String = "USD",
                       val offer: OfferEntity,
                       val creditCard: CreditCardEntity? = null) {

    fun toPrice(): Price {
        return Price(amount = amount, currency = currency, offer = offer.toOffer(), creditCard = creditCard?.toCreditCard())
    }
}
