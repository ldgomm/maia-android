package com.premierdarkcoffee.sales.maia.root.feature.product.data.remote.dto

import com.premierdarkcoffee.sales.maia.root.feature.product.domain.model.product.Information
import kotlinx.serialization.Serializable

@Serializable
data class InformationDto(val id: String,
                          val title: String,
                          val subtitle: String,
                          val description: String,
                          val image: ImageDto,
                          val place: Int) {

    fun toInformation(): Information {
        return Information(id = id,
                           title = title,
                           subtitle = subtitle,
                           description = description,
                           image = image.toImage(),
                           place = place)
    }
}
