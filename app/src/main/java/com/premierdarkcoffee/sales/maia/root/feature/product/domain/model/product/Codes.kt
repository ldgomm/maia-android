package com.premierdarkcoffee.sales.maia.root.feature.product.domain.model.product

data class Codes(val EAN: String) {

    fun toCodesDto(): CodesDto {
        return CodesDto(EAN)
    }
}
