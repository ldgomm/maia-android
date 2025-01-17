package com.premierdarkcoffee.sales.maia.root.navigation.route

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
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

        // Remembered state to hold the sign-in result
        var signInResult by remember { mutableStateOf(Pair(false, "Default message.")) }

        // Pass handleSignIn as a lambda that updates signInResult asynchronously
        AuthenticationView(
            handleSignIn = { email, password ->
                coroutineScope.launch {
                    try {
                        viewModel.signInWithEmail(email = email, password = password, onSuccess = { token ->
                            // Mark as success
                            signInResult = Pair(true, "Authentication successfully.")
                            // Store the JWT
                            JwtSecurePreferencesHelper.setJwt(context, jwt = token)
                            // Navigate away
                            onNavigateToProductsViewTriggered()
                        }, onFailure = { exception ->
                            signInResult = Pair(
                                false, exception.message ?: "Unexpected error occurred."
                            )
                        })
                    } catch (e: Exception) {
                        signInResult = Pair(false, e.message ?: "Unexpected error occurred.")
                    }
                }
            }, signInResult = signInResult
        )
    }
}