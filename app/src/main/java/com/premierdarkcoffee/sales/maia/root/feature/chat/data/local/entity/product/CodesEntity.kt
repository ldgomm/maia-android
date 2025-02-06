package com.premierdarkcoffee.sales.maia.root.feature.chat.data.local.entity.product

import com.premierdarkcoffee.sales.maia.root.feature.product.domain.model.product.Codes

data class CodesEntity(val EAN: String) {

    fun toCodes(): Codes {
        return  Codes(EAN)
    }
}