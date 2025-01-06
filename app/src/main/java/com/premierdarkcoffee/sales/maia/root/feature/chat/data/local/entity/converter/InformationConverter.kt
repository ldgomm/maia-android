package com.premierdarkcoffee.sales.maia.root.feature.chat.data.local.entity.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.premierdarkcoffee.sales.maia.root.feature.chat.data.local.entity.product.InformationEntity

class InformationConverter {

    @TypeConverter
    fun fromInformationEntityList(list: List<InformationEntity>?): String? {
        return list?.let { Gson().toJson(it) }
    }

    @TypeConverter
    fun toInformationEntityList(value: String?): List<InformationEntity>? {
        return value?.let { Gson().fromJson(it, object : TypeToken<List<InformationEntity>>() {}.type) }
    }

}