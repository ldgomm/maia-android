package com.premierdarkcoffee.sales.maia.root.feature.product.domain.model.product.request

import com.premierdarkcoffee.sales.maia.root.feature.product.data.remote.dto.ProductDto
import kotlinx.serialization.Serializable

@Serializable
data class PostProductRequest(val key: String? = null, val product: ProductDto)

//fun getStoreKey(): String {
////    val apiKey = BuildConfig.API_KEY
//
//
//    return "gtdTtFrhFtreZUwBojaUXQFDabA9ZRFD53UbNeZzEasYWF77TqFHSY7GWX9vtCyUFvuDx9YPpykWtri3Qu6JM3bbAntMVftPLuVS"
//}