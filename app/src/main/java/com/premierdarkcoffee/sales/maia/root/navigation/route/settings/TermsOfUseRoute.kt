package com.premierdarkcoffee.sales.maia.root.navigation.route.settings

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.premierdarkcoffee.sales.maia.root.feature.settings.presentation.view.settings.info.TermsOfUseView
import com.premierdarkcoffee.sales.maia.root.navigation.TermsOfUseRoute

fun NavGraphBuilder.termsOfUseRoute() {

    composable<TermsOfUseRoute> {
        TermsOfUseView()
    }
}
