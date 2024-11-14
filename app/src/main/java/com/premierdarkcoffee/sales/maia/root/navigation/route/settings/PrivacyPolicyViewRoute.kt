package com.premierdarkcoffee.sales.maia.root.navigation.route.settings

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.premierdarkcoffee.sales.maia.root.feature.settings.presentation.view.settings.PrivacyPolicyView
import com.premierdarkcoffee.sales.maia.root.navigation.PrivacyPolicyRoute

fun NavGraphBuilder.privacyPolicyView() {

    composable<PrivacyPolicyRoute> {
        PrivacyPolicyView()
    }
}
