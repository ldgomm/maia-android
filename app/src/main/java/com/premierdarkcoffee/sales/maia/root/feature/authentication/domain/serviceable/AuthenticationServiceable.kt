package com.premierdarkcoffee.sales.maia.root.feature.authentication.domain.serviceable

import com.premierdarkcoffee.sales.maia.root.feature.authentication.domain.model.LoginResponse
import com.premierdarkcoffee.sales.maia.root.feature.authentication.domain.model.PostStoreRequest
import kotlinx.coroutines.flow.Flow

interface AuthenticationServiceable {

    fun postStore(endpoint: String, request: PostStoreRequest): Flow<Result<LoginResponse>>
}
