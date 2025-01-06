package com.premierdarkcoffee.sales.maia.root.navigation.route.settings

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.premierdarkcoffee.sales.maia.root.feature.settings.presentation.view.settings.AccountDeletionView
import com.premierdarkcoffee.sales.maia.root.navigation.AccountDeletionRoute

fun NavGraphBuilder.accountDeletionRoute() {

    composable<AccountDeletionRoute> {
        AccountDeletionView()
    }
}
