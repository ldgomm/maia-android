package com.premierdarkcoffee.sales.maia.root.feature.chat.data.local.entity.product

import com.premierdarkcoffee.sales.maia.root.feature.product.domain.model.product.Image

data class ImageEntity(val path: String? = null, val url: String, val belongs: Boolean) {

    fun toImage(): Image {
        return Image(path = path, url = url, belongs = belongs)
    }
}
