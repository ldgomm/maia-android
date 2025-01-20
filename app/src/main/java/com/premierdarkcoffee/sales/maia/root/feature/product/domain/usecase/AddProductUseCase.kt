package com.premierdarkcoffee.sales.maia.root.feature.product.domain.usecase

import com.premierdarkcoffee.sales.maia.root.feature.product.domain.model.product.request.PostProductRequest
import com.premierdarkcoffee.sales.maia.root.feature.product.domain.model.product.response.MessageResponse
import com.premierdarkcoffee.sales.maia.root.feature.product.domain.serviceable.ProductServiceable
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AddProductUseCase @Inject constructor(private val productServiceable: ProductServiceable) {

    operator fun invoke(url: String, request: PostProductRequest, token: String): Flow<Result<MessageResponse>> {
        return productServiceable.addProduct(url, request, token)
    }
}
