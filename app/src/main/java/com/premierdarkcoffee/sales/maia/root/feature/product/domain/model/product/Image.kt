package com.premierdarkcoffee.sales.maia.root.feature.product.domain.model.product

import com.premierdarkcoffee.sales.maia.root.feature.chat.data.local.entity.product.ImageEntity
import com.premierdarkcoffee.sales.maia.root.feature.product.data.remote.dto.ImageDto

data class Image(val path: String? = null, val url: String, val belongs: Boolean) {

    fun toImageDto(): ImageDto {
        return ImageDto(path = path, url = url, belongs = belongs)
    }

    fun toImageEntity(): ImageEntity {
        return ImageEntity(path = path, url = url, belongs = belongs)
    }
}
