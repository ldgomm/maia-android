package com.premierdarkcoffee.sales.maia.root.feature.product.domain.model.product

import com.premierdarkcoffee.sales.maia.root.feature.product.data.remote.dto.CodesDto

data class Codes(val EAN: String) {

    fun toCodesDto(): CodesDto {
        return CodesDto(EAN)
    }
}
