package com.premierdarkcoffee.sales.maia.root.feature.chat.domain.model.store

import com.premierdarkcoffee.sales.maia.root.feature.chat.data.local.entity.store.GeoPointEntity
import com.premierdarkcoffee.sales.maia.root.feature.chat.data.remote.dto.store.GeoPointDto

data class GeoPoint(val type: String, val coordinates: List<Double>) {

    fun toGeoPointDto(): GeoPointDto {
        return GeoPointDto(type, coordinates)
    }

    fun toGeoPointEntity(): GeoPointEntity {
        return GeoPointEntity(type, coordinates)
    }
}
