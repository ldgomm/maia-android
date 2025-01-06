package com.premierdarkcoffee.sales.maia.root.feature.product.data.remote.dto

import com.premierdarkcoffee.sales.maia.root.feature.product.domain.model.product.Image
import kotlinx.serialization.Serializable

@Serializable
data class ImageDto(val path: String? = null, val url: String, val belongs: Boolean) {

    fun toImage(): Image {
        return Image(path = path, url = url, belongs = belongs)
    }
}
