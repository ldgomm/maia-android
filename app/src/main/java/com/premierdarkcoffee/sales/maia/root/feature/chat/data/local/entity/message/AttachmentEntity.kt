package com.premierdarkcoffee.sales.maia.root.feature.chat.data.local.entity.message

//
//  AttachmentEntity.kt
//  Maia
//
//  Created by José Ruiz on 14/7/24.
//

import com.premierdarkcoffee.sales.maia.root.feature.chat.data.remote.dto.message.AttachmentDto
import com.premierdarkcoffee.sales.maia.root.feature.chat.data.remote.dto.message.AttachmentTypeDto
import java.util.UUID

data class AttachmentEntity(val id: String = UUID.randomUUID().toString(),
                            val url: String,
                            val type: AttachmentTypeEntity,
                            val size: Long,
                            val name: String) {

    fun toAttachmentDto(): AttachmentDto {
        return AttachmentDto(url = url, type = AttachmentTypeDto.valueOf(type.name), size = size, name = name)
    }
}
