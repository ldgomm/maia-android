package com.premierdarkcoffee.sales.maia.root.feature.chat.data.local.entity.converter

//
//  LocationConverter.kt
//  Maia
//
//  Created by José Ruiz on 26/7/24.
//

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.premierdarkcoffee.sales.maia.root.feature.chat.data.local.entity.store.GeoPointEntity

class GeoPointConverter {
    @TypeConverter
    fun fromGeoPointEntity(locationEntity: GeoPointEntity?): String? {
        return locationEntity?.let { Gson().toJson(it) }
    }

    @TypeConverter
    fun toGeoPointEntity(json: String?): GeoPointEntity? {
        return json?.let { Gson().fromJson(it, GeoPointEntity::class.java) }
    }
}