package com.premierdarkcoffee.sales.maia.root.feature.chat.data.local.entity.store

import com.premierdarkcoffee.sales.maia.root.feature.chat.domain.model.store.Address

data class AddressEntity(val street: String,
                         val city: String,
                         val state: String,
                         val zipCode: String,
                         val country: String,
                         val location: GeoPointEntity) {

    fun toAddress(): Address {
        return Address(street, city, state, zipCode, country, location.toGeoPoint())
    }
}
