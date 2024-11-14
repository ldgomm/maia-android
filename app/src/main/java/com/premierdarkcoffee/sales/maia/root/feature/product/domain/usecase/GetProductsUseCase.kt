package com.premierdarkcoffee.sales.maia.root.feature.product.domain.usecase

import com.premierdarkcoffee.sales.maia.root.feature.product.data.remote.dto.ProductDto
import com.premierdarkcoffee.sales.maia.root.feature.product.domain.serviceable.ProductServiceable
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetProductsUseCase @Inject constructor(private val productServiceable: ProductServiceable) {

    operator fun invoke(
        url: String,
        token: String
    ): Flow<Result<List<ProductDto>>> {
        return productServiceable.getProducts(url, token)
    }
}
