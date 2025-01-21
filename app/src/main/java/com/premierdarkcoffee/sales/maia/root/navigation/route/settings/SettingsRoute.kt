package com.premierdarkcoffee.sales.maia.root.navigation.route.settings

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.premierdarkcoffee.sales.maia.root.feature.settings.presentation.view.settings.SettingsView
import com.premierdarkcoffee.sales.maia.root.feature.settings.presentation.viewmodel.SettingsViewModel
import com.premierdarkcoffee.sales.maia.root.navigation.SettingsRoute
import com.premierdarkcoffee.sales.maia.root.util.function.sharedViewModel

fun NavGraphBuilder.settingsRoute(navController: NavHostController,
                                  onNavigateToPrivacyPolicyButtonClicked: () -> Unit,
                                  onNavigateToTermsOfUseButtonClicked: () -> Unit,
                                  onNavigateToAccountDeletionButtonClicked: () -> Unit,
                                  onLogoutButtonClicked: () -> Unit) {
    composable<SettingsRoute> { backStackEntry ->
        val viewModel = backStackEntry.sharedViewModel<SettingsViewModel>(navController = navController)

        SettingsView(onNavigateToPrivacyPolicyButtonClicked,
                     onNavigateToTermsOfUseButtonClicked,
                     onNavigateToAccountDeletionButtonClicked,
                     onLogoutButtonClicked)
    }
}