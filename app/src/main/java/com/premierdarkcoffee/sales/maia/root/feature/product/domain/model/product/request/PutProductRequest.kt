package com.premierdarkcoffee.sales.maia.root.feature.product.domain.model.product.request

import com.premierdarkcoffee.sales.maia.root.feature.product.data.remote.dto.ProductDto
import kotlinx.serialization.Serializable

@Serializable
data class PutProductRequest(val key: String? = null, val product: ProductDto)
