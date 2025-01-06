package com.premierdarkcoffee.sales.maia.root.feature.chat.data.local.entity.store

import com.premierdarkcoffee.sales.maia.root.feature.chat.data.local.entity.product.ImageEntity
import com.premierdarkcoffee.sales.maia.root.feature.chat.domain.model.store.Store

data class StoreEntity(
    val id: String,
    val name: String,
    val image: ImageEntity,
    val address: AddressEntity,
    val phoneNumber: String,
    val emailAddress: String,
    val website: String,
    val description: String,
    val returnPolicy: String,
    val refundPolicy: String,
    val brands: List<String>,
    val createdAt: Long,
    val status: StoreStatusEntity
) {

    fun toStore(): Store {
        return Store(
            id = id,
            name = name,
            image = image.toImage(),
            address = address.toAddress(),
            phoneNumber = phoneNumber,
            emailAddress = emailAddress,
            website = website,
            description = description,
            returnPolicy = returnPolicy,
            refundPolicy = refundPolicy,
            brands = brands,
            createdAt = createdAt,
            status = status.toStoreStatus()
        )
    }
}