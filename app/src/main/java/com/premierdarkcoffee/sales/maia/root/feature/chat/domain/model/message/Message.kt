package com.premierdarkcoffee.sales.maia.root.feature.chat.domain.model.message

//
//  Message.kt
//  Maia
//
//  Created by José Ruiz on 14/7/24.
//

import com.premierdarkcoffee.sales.maia.root.feature.chat.data.local.entity.message.MessageEntity
import com.premierdarkcoffee.sales.maia.root.feature.chat.data.local.entity.message.MessageStatusEntity
import com.premierdarkcoffee.sales.maia.root.feature.chat.data.local.entity.message.MessageTypeEntity
import com.premierdarkcoffee.sales.maia.root.feature.chat.data.remote.dto.message.MessageDto
import com.premierdarkcoffee.sales.maia.root.feature.chat.data.remote.dto.message.MessageStatusDto
import com.premierdarkcoffee.sales.maia.root.feature.chat.data.remote.dto.message.MessageTypeDto
import java.util.UUID

data class Message(
    val id: String = UUID.randomUUID().toString(),
    val text: String,
    val fromClient: Boolean = true,
    val date: Long = System.currentTimeMillis(),
    val clientId: String,
    val storeId: String,
    val status: MessageStatus = MessageStatus.SENT,
    val type: MessageType = MessageType.TEXT,
    val attachment: Attachment? = null,
    val product: String? = null
) {

    fun toMessageDto(): MessageDto {
        return MessageDto(
            id = id,
            text = text,
            fromClient = fromClient,
            date = date,
            clientId = clientId,
            storeId = storeId,
            status = MessageStatusDto.valueOf(status.name),
            type = MessageTypeDto.valueOf(type.name),
            attachment = attachment?.toAttachmentDto(),
            product = product
        )
    }

    fun toMessageEntity(): MessageEntity {
        return MessageEntity(
            id = id,
            text = text,
            fromClient = fromClient,
            date = date,
            clientId = clientId,
            storeId = storeId,
            status = MessageStatusEntity.valueOf(status.name),
            type = MessageTypeEntity.valueOf(type.name),
            attachment = attachment?.toAttachmentEntity(),
            product = product
        )
    }
}
