package com.premierdarkcoffee.sales.maia.root.feature.chat.data.remote.dto.message

//
//  MessageDto.kt
//  Maia
//
//  Created by José Ruiz on 14/7/24.
//

import com.premierdarkcoffee.sales.maia.root.feature.chat.data.local.entity.message.MessageEntity
import com.premierdarkcoffee.sales.maia.root.feature.chat.data.local.entity.message.MessageStatusEntity
import com.premierdarkcoffee.sales.maia.root.feature.chat.data.local.entity.message.MessageTypeEntity
import com.premierdarkcoffee.sales.maia.root.feature.chat.domain.model.message.Message
import com.premierdarkcoffee.sales.maia.root.feature.chat.domain.model.message.MessageStatus
import com.premierdarkcoffee.sales.maia.root.feature.chat.domain.model.message.MessageType
import kotlinx.serialization.Serializable
import java.util.UUID

@Serializable
data class MessageDto(val id: String = UUID.randomUUID().toString(),
                      val text: String = "",
                      val fromClient: Boolean = true,
                      val date: Long = System.currentTimeMillis(),
                      val clientId: String = "",
                      val storeId: String = "",
                      val status: MessageStatusDto = MessageStatusDto.SENT,
                      val type: MessageTypeDto = MessageTypeDto.TEXT,
                      val attachment: AttachmentDto? = null,
                      val product: String? = null) {

    fun toMessage(): Message {
        return Message(id = id,
                       text = text,
                       fromClient = fromClient,
                       date = date,
                       clientId = clientId,
                       storeId = storeId,
                       status = MessageStatus.valueOf(status.name),
                       type = MessageType.valueOf(type.name),
                       attachment = attachment?.toAttachment(),
                       product = product)
    }

    fun toMessageEntity(): MessageEntity {
        return MessageEntity(id = id,
                             text = text,
                             fromClient = fromClient,
                             date = date,
                             clientId = clientId,
                             storeId = storeId,
                             status = MessageStatusEntity.valueOf(status.name),
                             type = MessageTypeEntity.valueOf(type.name),
                             attachment = attachment?.toAttachmentEntity(),
                             product = product)
    }
}
