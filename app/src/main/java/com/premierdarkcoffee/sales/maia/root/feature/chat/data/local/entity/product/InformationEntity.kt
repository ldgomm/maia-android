package com.premierdarkcoffee.sales.maia.root.feature.chat.data.local.entity.product

import com.premierdarkcoffee.sales.maia.root.feature.product.domain.model.product.Information


data class InformationEntity(val id: String,
                             val title: String,
                             val subtitle: String,
                             val description: String,
                             val image: ImageEntity,
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