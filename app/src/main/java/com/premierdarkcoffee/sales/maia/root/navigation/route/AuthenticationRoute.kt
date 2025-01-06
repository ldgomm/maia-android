package com.premierdarkcoffee.sales.maia.root.navigation.route

import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.premierdarkcoffee.sales.maia.root.feature.authentication.presentation.view.AuthenticationView
import com.premierdarkcoffee.sales.maia.root.feature.authentication.presentation.viewmodel.AuthenticationViewModel
import com.premierdarkcoffee.sales.maia.root.navigation.AuthenticationRoute
import com.premierdarkcoffee.sales.maia.root.util.helper.JwtSecurePreferencesHelper
import kotlinx.coroutines.launch

fun NavGraphBuilder.authenticationRoute(onNavigateToProductsViewTriggered: () -> Unit) {
    composable<AuthenticationRoute> {

        val viewModel: AuthenticationViewModel = hiltViewModel()
        val coroutineScope = rememberCoroutineScope()
        val context = LocalContext.current

        AuthenticationView { email, password ->
            var result = Pair(false, "Something went wrong. Please try again.") // Default response
            coroutineScope.launch {
                try {
                    viewModel.signInWithEmail(email = email, password = password, onSuccess = { token ->
                        result = Pair(true, "")
                        JwtSecurePreferencesHelper.setJwt(context, jwt = token)
                        onNavigateToProductsViewTriggered()
                    }, onFailure = { exception ->
                        result = Pair(false, exception.message ?: "Unexpected error occurred.")
                    })
                } catch (e: Exception) {
                    result = Pair(false, e.message ?: "Unexpected error occurred.")
                }
            }
            result
        }
    }
}
