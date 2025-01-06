package com.premierdarkcoffee.sales.maia.root.feature.product.domain.serviceable

import com.premierdarkcoffee.sales.maia.root.feature.product.data.remote.dto.ProductDto
import kotlinx.coroutines.flow.Flow

interface SearchServiceable {

    fun getListOfData(
        endpoint: String,
        token: String
    ): Flow<Result<List<ProductDto>>>
}

