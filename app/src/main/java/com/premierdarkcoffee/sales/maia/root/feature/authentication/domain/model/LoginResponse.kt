package com.premierdarkcoffee.sales.maia.root.feature.authentication.domain.model

//
//  LoginResponse.kt
//  Maia
//
//  Created by José Ruiz on 8/10/24.
//

import kotlinx.serialization.Serializable

@Serializable
data class LoginResponse(val token: String)