package com.premierdarkcoffee.sales.maia.root.feature.chat.data.local.entity.product

import com.premierdarkcoffee.sales.maia.root.feature.product.domain.model.product.Product

data class ProductEntity(
    val id: String,
    val name: String,
    val model: String,
    val description: String,
    val category: CategoryEntity,
    val price: PriceEntity,
    val stock: Int,
    val image: ImageEntity,
    val origin: String,
    val date: Long,
    val overview: List<InformationEntity>,
    val keywords: List<String>,
    val specifications: SpecificationsEntity? = null,
    val warranty: WarrantyEntity? = null,
    val legal: String? = null,
    val warning: String? = null,
    val storeId: String? = null
) {

    fun toProduct(): Product {
        return Product(
            id = id,
            name = name,
            model = model,
            description = description,
            category = category.toCategory(),
            price = price.toPrice(),
            stock = stock,
            image = image.toImage(),
            origin = origin,
            date = date,
            overview = overview.map { it.toInformation() },
            keywords = keywords,
            specifications = specifications?.toSpecifications(),
            warranty = warranty?.toWarranty(),
            legal = legal,
            warning = warning,
            storeId = storeId
        )
    }
}