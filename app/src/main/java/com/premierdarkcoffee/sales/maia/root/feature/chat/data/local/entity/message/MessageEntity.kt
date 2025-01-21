package com.premierdarkcoffee.sales.maia.root.feature.chat.data.local.entity.message

//
//  MessageEntity.kt
//  Maia
//
//  Created by José Ruiz on 14/7/24.
//

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.premierdarkcoffee.sales.maia.root.feature.chat.data.remote.dto.message.MessageDto
import com.premierdarkcoffee.sales.maia.root.feature.chat.data.remote.dto.message.MessageStatusDto
import com.premierdarkcoffee.sales.maia.root.feature.chat.data.remote.dto.message.MessageTypeDto

@Entity(tableName = "messages")
data class MessageEntity(@PrimaryKey(autoGenerate = false) val id: String,
                         val text: String,
                         val fromClient: Boolean,
                         val date: Long,
                         val clientId: String,
                         val storeId: String,
                         var status: MessageStatusEntity = MessageStatusEntity.SENT,
                         val type: MessageTypeEntity = MessageTypeEntity.TEXT,
                         val attachment: AttachmentEntity? = null,
                         val product: String? = null) {

    fun toMessageDto(): MessageDto {
        return MessageDto(id = id,
                          text = text,
                          fromClient = fromClient,
                          date = date,
                          clientId = clientId,
                          storeId = storeId,
                          status = MessageStatusDto.valueOf(status.name),
                          type = MessageTypeDto.valueOf(type.name),
                          attachment = attachment?.toAttachmentDto(),
                          product = product)
    }
}
