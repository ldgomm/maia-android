package com.premierdarkcoffee.sales.maia.root.feature.product.domain.usecase

import com.premierdarkcoffee.sales.maia.root.feature.product.domain.model.product.request.DeleteProductRequest
import com.premierdarkcoffee.sales.maia.root.feature.product.domain.model.product.response.MessageResponse
import com.premierdarkcoffee.sales.maia.root.feature.product.domain.serviceable.ProductServiceable
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DeleteProductUseCase @Inject constructor(private val productServiceable: ProductServiceable) {

    operator fun invoke(url: String, request: DeleteProductRequest, token: String): Flow<Result<MessageResponse>> {
        return productServiceable.deleteProduct(url, request, token)
    }
}
