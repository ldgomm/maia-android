package com.premierdarkcoffee.sales.maia.root.util.constant

object Constant {
    const val clientId: String = "495878670818-bud1damggt69ge5hv1eo8gqdg4c1efph.apps.googleusercontent.com"

    private const val productRouteDestination = "com.premierdarkcoffee.sales.maia.root.navigation.ProductRoute/{product}"
    private const val addEditProductRouteDestination =
        "com.premierdarkcoffee.sales.maia.root.navigation.AddEditProductRoute/{product}"
    private const val conversationRouteDestination =
        "com.premierdarkcoffee.sales.maia.root.navigation.ConversationRoute?clientId={clientId}"

    val ignore = listOf(productRouteDestination, addEditProductRouteDestination, conversationRouteDestination)
}
