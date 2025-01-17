package com.premierdarkcoffee.sales.maia.root.navigation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.AccountCircle
import androidx.compose.material.icons.sharp.Email
import androidx.compose.material.icons.sharp.Menu
import androidx.compose.material.icons.sharp.Search
import androidx.compose.material.icons.sharp.Settings
import androidx.compose.ui.graphics.vector.ImageVector
import com.premierdarkcoffee.sales.maia.R
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable

@Serializable
sealed class Route(
    @StringRes val resourceId: Int,
    @Contextual var icon: ImageVector? = null
)

@Serializable
object AuthenticationRoute

@Serializable
data object ProductsRoute : Route(resourceId = R.string.myProducts, icon = Icons.Sharp.Menu)

@Serializable
data class ProductRoute(val product: String)

@Serializable
data class AddEditProductRoute(val product: String)

@Serializable
data object SearchRoute : Route(resourceId = R.string.search, icon = Icons.Sharp.Search)

@Serializable
data object ChatsRoute : Route(resourceId = R.string.chats, icon = Icons.Sharp.Email)

@Serializable
data class ConversationRoute(val clientId: String? = null)

@Serializable
data object StoreRoute : Route(resourceId = R.string.store, icon = Icons.Sharp.AccountCircle)

@Serializable
data object SettingsRoute : Route(resourceId = R.string.settings, icon = Icons.Sharp.Settings)

@Serializable
object AccountDeletionRoute

@Serializable
object TermsOfUseRoute

@Serializable
object PrivacyPolicyRoute
