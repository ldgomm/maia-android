package com.premierdarkcoffee.sales.maia.root.feature.product.domain.state

import com.premierdarkcoffee.sales.maia.root.feature.product.domain.model.product.Product

data class ProductsState(val products: List<Product>? = null)
