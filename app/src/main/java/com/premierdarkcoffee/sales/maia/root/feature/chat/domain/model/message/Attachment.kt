package com.premierdarkcoffee.sales.maia.root.feature.chat.domain.model.message

//
//  Attachment.kt
//  Maia
//
//  Created by José Ruiz on 14/7/24.
//

import com.premierdarkcoffee.sales.maia.root.feature.chat.data.local.entity.message.AttachmentEntity
import com.premierdarkcoffee.sales.maia.root.feature.chat.data.local.entity.message.AttachmentTypeEntity
import com.premierdarkcoffee.sales.maia.root.feature.chat.data.remote.dto.message.AttachmentDto
import com.premierdarkcoffee.sales.maia.root.feature.chat.data.remote.dto.message.AttachmentTypeDto

data class Attachment(val url: String, val type: AttachmentType, val size: Long, val name: String) {

    fun toAttachmentDto(): AttachmentDto {
        return AttachmentDto(url = url, type = AttachmentTypeDto.valueOf(type.name), size = size, name = name)
    }

    fun toAttachmentEntity(): AttachmentEntity {
        return AttachmentEntity(url = url, type = AttachmentTypeEntity.valueOf(type.name), size = size, name = name)
    }
}
