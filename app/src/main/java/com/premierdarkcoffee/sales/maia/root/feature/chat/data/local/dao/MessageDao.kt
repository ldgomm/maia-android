package com.premierdarkcoffee.sales.maia.root.feature.chat.data.local.dao

//
//  MessageDao.kt
//  Maia
//
//  Created by José Ruiz on 14/7/24.
//

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.premierdarkcoffee.sales.maia.root.feature.chat.data.local.entity.message.MessageEntity

@Dao
interface MessageDao {
    @Query("SELECT * FROM messages")
    fun getAllMessages(): List<MessageEntity>

    @Query("SELECT * FROM messages WHERE id = :messageId")
    fun getMessageById(messageId: String): MessageEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMessage(message: MessageEntity)

    @Update
    fun updateMessage(message: MessageEntity)

    @Delete
    fun deleteMessage(message: MessageEntity)
}
