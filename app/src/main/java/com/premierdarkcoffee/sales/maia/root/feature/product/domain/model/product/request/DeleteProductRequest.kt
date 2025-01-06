package com.premierdarkcoffee.sales.maia.root.feature.product.domain.model.product.request

import kotlinx.serialization.Serializable

@Serializable
data class DeleteProductRequest(val key: String? = null, val productId: String)
