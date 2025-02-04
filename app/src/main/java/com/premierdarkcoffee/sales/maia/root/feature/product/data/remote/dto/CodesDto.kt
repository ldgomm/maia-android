package com.premierdarkcoffee.sales.maia.root.feature.product.data.remote.dto

import com.premierdarkcoffee.sales.maia.root.feature.product.domain.model.product.Codes
import kotlinx.serialization.Serializable

@Serializable
data class CodesDto(val EAN: String) {

    fun toCodes(): Codes {
        return Codes(EAN)
    }
}
