package com.premierdarkcoffee.sales.maia.root.feature.authentication.domain.model

import com.premierdarkcoffee.sales.maia.root.feature.chat.data.remote.dto.store.StoreDto
import kotlinx.serialization.Serializable

@Serializable
data class PostStoreRequest(val key: String? = null, val store: StoreDto)
