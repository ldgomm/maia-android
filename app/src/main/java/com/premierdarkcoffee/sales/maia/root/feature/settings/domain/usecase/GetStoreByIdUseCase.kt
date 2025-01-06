package com.premierdarkcoffee.sales.maia.root.feature.settings.domain.usecase

//
//  GetStoreByIdUseCase.kt
//  Maia
//
//  Created by José Ruiz on 31/7/24.
//

import com.premierdarkcoffee.sales.maia.root.feature.chat.data.remote.dto.store.StoreDto
import com.premierdarkcoffee.sales.maia.root.feature.settings.domain.serviceable.StoreServiceable
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetStoreByIdUseCase @Inject constructor(private val storeServiceable: StoreServiceable) {

    operator fun invoke(
        url: String,
        token: String
    ): Flow<Result<StoreDto>> {
        return storeServiceable.getStoreById(url, token)
    }
}