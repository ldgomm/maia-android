package com.premierdarkcoffee.sales.maia.root.feature.product.domain.serviceable

import com.premierdarkcoffee.sales.maia.root.feature.product.data.remote.dto.ProductDto
import com.premierdarkcoffee.sales.maia.root.feature.product.domain.model.product.request.DeleteProductRequest
import com.premierdarkcoffee.sales.maia.root.feature.product.domain.model.product.request.PostProductRequest
import com.premierdarkcoffee.sales.maia.root.feature.product.domain.model.product.request.PutProductRequest
import com.premierdarkcoffee.sales.maia.root.feature.product.domain.model.product.response.MessageResponse
import kotlinx.coroutines.flow.Flow

interface ProductServiceable {

    fun getProducts(
        endpoint: String,
        token: String
    ): Flow<Result<List<ProductDto>>>

    fun addProduct(
        url: String,
        request: PostProductRequest,
        token: String
    ): Flow<Result<MessageResponse>>

    fun updateProduct(
        url: String,
        request: PutProductRequest,
        token: String
    ): Flow<Result<MessageResponse>>

    fun deleteProduct(
        url: String,
        request: DeleteProductRequest,
        token: String
    ): Flow<Result<MessageResponse>>
}
