package com.premierdarkcoffee.sales.maia.root.feature.product.domain.usecase

import com.premierdarkcoffee.sales.maia.root.feature.product.domain.model.product.request.PutProductRequest
import com.premierdarkcoffee.sales.maia.root.feature.product.domain.model.product.response.MessageResponse
import com.premierdarkcoffee.sales.maia.root.feature.product.domain.serviceable.ProductServiceable
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UpdateProductUseCase @Inject constructor(private val productServiceable: ProductServiceable) {

    operator fun invoke(
        url: String,
        request: PutProductRequest,
        token: String
    ): Flow<Result<MessageResponse>> {
        return productServiceable.updateProduct(url, request, token)
    }
}
