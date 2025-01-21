package com.premierdarkcoffee.sales.maia.root.feature.chat.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.premierdarkcoffee.sales.maia.root.feature.chat.data.local.dao.MessageDao
import com.premierdarkcoffee.sales.maia.root.feature.chat.data.local.entity.converter.AddressConverter
import com.premierdarkcoffee.sales.maia.root.feature.chat.data.local.entity.converter.CategoryConverter
import com.premierdarkcoffee.sales.maia.root.feature.chat.data.local.entity.converter.GeoPointConverter
import com.premierdarkcoffee.sales.maia.root.feature.chat.data.local.entity.converter.ImageConverter
import com.premierdarkcoffee.sales.maia.root.feature.chat.data.local.entity.converter.InformationConverter
import com.premierdarkcoffee.sales.maia.root.feature.chat.data.local.entity.converter.MessageConverter
import com.premierdarkcoffee.sales.maia.root.feature.chat.data.local.entity.converter.PriceConverter
import com.premierdarkcoffee.sales.maia.root.feature.chat.data.local.entity.converter.ProductConverter
import com.premierdarkcoffee.sales.maia.root.feature.chat.data.local.entity.converter.ProductListConverter
import com.premierdarkcoffee.sales.maia.root.feature.chat.data.local.entity.converter.SpecificationsConverter
import com.premierdarkcoffee.sales.maia.root.feature.chat.data.local.entity.converter.StoreConverter
import com.premierdarkcoffee.sales.maia.root.feature.chat.data.local.entity.converter.StoreStatusConverter
import com.premierdarkcoffee.sales.maia.root.feature.chat.data.local.entity.converter.StringConverter
import com.premierdarkcoffee.sales.maia.root.feature.chat.data.local.entity.converter.WarrantyConverter
import com.premierdarkcoffee.sales.maia.root.feature.chat.data.local.entity.message.MessageEntity

@Database(entities = [MessageEntity::class], version = 1)
@TypeConverters(ProductListConverter::class,
                CategoryConverter::class,
                ImageConverter::class,
                InformationConverter::class,
                PriceConverter::class,
                SpecificationsConverter::class,
                StringConverter::class,
                WarrantyConverter::class,
                StoreConverter::class,
                AddressConverter::class,
                GeoPointConverter::class,
                MessageConverter::class,
                ProductConverter::class,
                StoreStatusConverter::class)
abstract class MainDatabase : RoomDatabase() {
    abstract val messageDao: MessageDao
}
