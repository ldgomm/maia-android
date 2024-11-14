package com.premierdarkcoffee.sales.maia.root.feature.chat.data.local.entity.product

import com.premierdarkcoffee.sales.maia.root.feature.product.domain.model.product.Specifications

data class SpecificationsEntity(val colours: List<String>,
                                val finished: String? = null,
                                val inBox: List<String>? = null,
                                val size: SizeEntity? = null,
                                val weight: WeightEntity? = null) {

    fun toSpecifications(): Specifications {
        return Specifications(colours = colours,
                              finished = finished,
                              inBox = inBox,
                              size = size?.toSize(),
                              weight = weight?.toWeight())
    }
}
