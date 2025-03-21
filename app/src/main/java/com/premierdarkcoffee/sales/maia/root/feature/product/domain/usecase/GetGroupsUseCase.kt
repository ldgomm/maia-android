package com.premierdarkcoffee.sales.maia.root.feature.product.domain.usecase

import com.premierdarkcoffee.sales.maia.root.feature.product.domain.serviceable.GroupServiceable
import com.premierdarkcoffee.sales.maia.root.feature.product.domain.serviceable.Group
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetGroupsUseCase @Inject constructor(private val groupServiceable: GroupServiceable) {

    operator fun invoke(url: String): Flow<Result<List<Group>>> {
        return groupServiceable.getGroups(url)
    }
}
