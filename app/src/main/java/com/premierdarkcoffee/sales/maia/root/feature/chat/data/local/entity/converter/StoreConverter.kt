package com.premierdarkcoffee.sales.maia.root.feature.chat.data.local.entity.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.premierdarkcoffee.sales.maia.root.feature.chat.data.local.entity.store.StoreEntity

class StoreConverter {

    @TypeConverter
    fun fromStoreEntity(storeEntity: StoreEntity?): String? {
        return storeEntity?.let { Gson().toJson(it) }
    }

    @TypeConverter
    fun toStoreEntity(json: String?): StoreEntity? {
        return json?.let { Gson().fromJson(it, StoreEntity::class.java) }
    }
}
