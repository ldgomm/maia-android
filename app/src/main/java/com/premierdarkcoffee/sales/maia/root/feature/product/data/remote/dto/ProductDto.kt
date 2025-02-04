package com.premierdarkcoffee.sales.maia.root.feature.product.data.remote.dto

import com.premierdarkcoffee.sales.maia.root.feature.product.domain.model.product.Product
import kotlinx.serialization.Serializable

@Serializable
data class ProductDto(val id: String,
                      val name: String,
                      val label: String? = null,
                      val owner: String? = null,
                      val year: String? = null,
                      val model: String,
                      val description: String,
                      val category: CategoryDto,
                      val price: PriceDto,
                      val stock: Int,
                      val image: ImageDto,
                      val origin: String,
                      val date: Long,
                      val overview: List<InformationDto>,
                      val keywords: List<String>? = null,
                      val codes: CodesDto? = null,
                      val specifications: SpecificationsDto? = null,
                      val warranty: String? = null,
                      val legal: String? = null,
                      val warning: String? = null,
                      val storeId: String? = null) {

    fun toProduct(): Product {
        return Product(id = id,
                       name = name,
                       label = label,
                       owner = owner,
                       year = year,
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
                       codes = codes?.toCodes(),
                       specifications = specifications?.toSpecifications(),
                       warranty = warranty,
                       legal = legal,
                       warning = warning,
                       storeId = storeId)
    }
}
