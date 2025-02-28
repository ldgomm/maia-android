package com.premierdarkcoffee.sales.maia.root.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.premierdarkcoffee.sales.maia.root.navigation.route.addEditProductRoute
import com.premierdarkcoffee.sales.maia.root.navigation.route.authenticationRoute
import com.premierdarkcoffee.sales.maia.root.navigation.route.chatsRoute
import com.premierdarkcoffee.sales.maia.root.navigation.route.conversationRoute
import com.premierdarkcoffee.sales.maia.root.navigation.route.productRoute
import com.premierdarkcoffee.sales.maia.root.navigation.route.productsRoute
import com.premierdarkcoffee.sales.maia.root.navigation.route.searchRoute
import com.premierdarkcoffee.sales.maia.root.navigation.route.settings.accountDeletionRoute
import com.premierdarkcoffee.sales.maia.root.navigation.route.settings.privacyPolicyView
import com.premierdarkcoffee.sales.maia.root.navigation.route.settings.settingsRoute
import com.premierdarkcoffee.sales.maia.root.navigation.route.settings.termsOfUseRoute
import com.premierdarkcoffee.sales.maia.root.navigation.route.storeRoute

@Composable
fun NavigationGraph(navController: NavHostController, startDestination: Any, modifier: Modifier = Modifier) {
    NavHost(navController = navController, startDestination = startDestination) {
        authenticationRoute(onNavigateToProductsViewTriggered = {
            navController.popBackStack()
            navController.navigate(ProductsRoute)
        })

        productsRoute(navController, onNavigateToProductView = { productJson: String ->
            navController.navigate(ProductRoute(productJson))
        })

        productRoute(navController,
                     onPopBackStackActionTriggered = { navController.popBackStack() },
                     onAddOrUpdateEditedProductButtonClick = {
                         navController.navigate(AddEditProductRoute(it))
                     })

        addEditProductRoute(navController = navController, onBackToProductsActionTriggered = {
            navController.navigate(ProductsRoute) {
                popUpTo(ProductsRoute) { inclusive = true }
            }
        })

//        addEditInformationRoute(navController = navController, onNavigateToAddEditProductViewActionTriggered = {
//            navController.popBackStack()
//            navController.navigate(AddEditProductRoute(null))
//        })

        searchRoute(navController = navController) { productJson: String ->
            navController.navigate(ProductRoute(productJson))
        }

        chatsRoute(navController = navController, onConversationItemViewClicked = { clientId ->
            navController.navigate(ConversationRoute(clientId = clientId))
        })

        conversationRoute(navController = navController, onNavigateToProductView = { productJson: String ->
            navController.navigate(ProductRoute(productJson))
        })

        storeRoute(navController = navController,
                   onNavigateToSettingsIconButtonClicked = { navController.navigate(SettingsRoute) })

        settingsRoute(navController,
                      onNavigateToPrivacyPolicyButtonClicked = {
                          navController.navigate(PrivacyPolicyRoute)
                      },
                      onNavigateToTermsOfUseButtonClicked = { navController.navigate(TermsOfUseRoute) },
                      onNavigateToAccountDeletionButtonClicked = {
                          navController.navigate(AccountDeletionRoute)
                      },
                      onLogoutButtonClicked = {
                          navController.navigate(AuthenticationRoute) {
                              popUpTo(0) {
                                  inclusive = true
                              }
                              launchSingleTop = true
                          }
                      })

        accountDeletionRoute()
        privacyPolicyView()
        termsOfUseRoute()
    }
}
