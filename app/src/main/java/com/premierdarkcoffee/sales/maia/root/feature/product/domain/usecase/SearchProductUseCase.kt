package com.premierdarkcoffee.sales.maia.root.feature.product.domain.usecase

import com.premierdarkcoffee.sales.maia.root.feature.product.data.remote.dto.ProductDto
import com.premierdarkcoffee.sales.maia.root.feature.product.domain.serviceable.SearchServiceable
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchProductUseCase @Inject constructor(private val serviceable: SearchServiceable) {

    operator fun invoke(url: String, token: String): Flow<Result<List<ProductDto>>> {
        return serviceable.getListOfData(url, token)
    }
}
