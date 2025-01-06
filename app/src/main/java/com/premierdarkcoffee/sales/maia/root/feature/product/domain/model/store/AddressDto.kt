package com.premierdarkcoffee.sales.maia.root.feature.product.domain.model.store

import kotlinx.serialization.Serializable

@Serializable
data class AddressDto(val street: String,
                      val city: String,
                      val state: String,
                      val zipCode: String,
                      val country: String,
                      val latitude: Double,
                      val longitude: Double)
