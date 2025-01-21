package com.premierdarkcoffee.sales.maia.root.feature.settings.domain.serviceable

import com.premierdarkcoffee.sales.maia.root.feature.chat.data.remote.dto.store.StoreDto
import kotlinx.coroutines.flow.Flow

//
//  StoreServiceable.kt
//  Maia
//
//  Created by José Ruiz on 31/7/24.
//

interface StoreServiceable {

    fun getStoreById(endpoint: String, token: String): Flow<Result<StoreDto>>
}
