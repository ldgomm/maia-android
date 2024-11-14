package com.premierdarkcoffee.sales.maia.root.feature.product.domain.model.store

import com.premierdarkcoffee.sales.maia.root.feature.chat.data.remote.dto.store.StoreStatusDto
import kotlinx.serialization.Serializable

@Serializable
data class StoreDto(val id: String,
                    val name: String,
                    val address: AddressDto,
                    val phoneNumber: String,
                    val emailAddress: String,
                    val website: String,
                    val description: String,
                    val returnPolicy: String,
                    val refundPolicy: String,
                    val brands: List<String>,
                    val status: StoreStatusDto)
