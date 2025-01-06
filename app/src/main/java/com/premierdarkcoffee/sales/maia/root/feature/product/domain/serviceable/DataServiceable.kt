package com.premierdarkcoffee.sales.maia.root.feature.product.domain.serviceable
//
//  DataServiceable.kt
//  Maia
//
//  Created by José Ruiz on 25/9/24.
//

import kotlinx.coroutines.flow.Flow
import kotlinx.serialization.Serializable

interface DataServiceable {

    fun addGroup(
        url: String,
        request: Group
    ): Flow<Result<Group>>


    fun getGroups(
        url: String,
    ): Flow<Result<List<Group>>>
}

@Serializable
data class Subclass(val name: String)

@Serializable
data class Domain(
    val name: String,
    val subclasses: List<Subclass>
)

@Serializable
data class Group(
    val name: String,
    val domains: List<Domain>
)



