package com.premierdarkcoffee.sales.maia.root.feature.authentication.domain.usecase

import com.premierdarkcoffee.sales.maia.root.feature.authentication.domain.model.LoginResponse
import com.premierdarkcoffee.sales.maia.root.feature.authentication.domain.model.PostStoreRequest
import com.premierdarkcoffee.sales.maia.root.feature.authentication.domain.serviceable.AuthenticationServiceable
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CreateStoreUseCase @Inject constructor(private val serviceable: AuthenticationServiceable) {

    operator fun invoke(
        url: String,
        request: PostStoreRequest
    ): Flow<Result<LoginResponse>> {
        return serviceable.postStore(endpoint = url, request = request)
    }
}
