package com.premierdarkcoffee.sales.maia.root.feature.chat.data.remote.dto.store

import com.premierdarkcoffee.sales.maia.root.feature.chat.domain.model.store.StoreStatus
import kotlinx.serialization.Serializable

@Serializable
data class StoreStatusDto(val isActive: Boolean,
                          val isVerified: Boolean,
                          val isPromoted: Boolean,
                          val isSuspended: Boolean,
                          val isClosed: Boolean,
                          val isPendingApproval: Boolean,
                          val isUnderReview: Boolean,
                          val isOutOfStock: Boolean,
                          val isOnSale: Boolean) {

    fun toStoreStatus(): StoreStatus {
        return StoreStatus(isActive = isActive,
                           isVerified = isVerified,
                           isPromoted = isPromoted,
                           isSuspended = isSuspended,
                           isClosed = isClosed,
                           isPendingApproval = isPendingApproval,
                           isUnderReview = isUnderReview,
                           isOutOfStock = isOutOfStock,
                           isOnSale = isOnSale)
    }
}
