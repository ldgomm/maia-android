package com.premierdarkcoffee.sales.maia.root.navigation.route

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.premierdarkcoffee.sales.maia.root.feature.authentication.presentation.view.AuthenticationView
import com.premierdarkcoffee.sales.maia.root.feature.authentication.presentation.viewmodel.AuthenticationViewModel
import com.premierdarkcoffee.sales.maia.root.navigation.AuthenticationRoute
import com.premierdarkcoffee.sales.maia.root.util.helper.SecurePreferencesHelper
import com.stevdzasan.onetap.rememberOneTapSignInState

fun NavGraphBuilder.authenticationRoute(onNavigateToProductsViewTriggered: () -> Unit) {
    composable<AuthenticationRoute> {
        val viewModel: AuthenticationViewModel = hiltViewModel()

        val signedInState by viewModel.signedInState
        val oneTapState = rememberOneTapSignInState()

        val context = LocalContext.current

        AuthenticationView(oneTapState = oneTapState, signedInState = signedInState, onSignInWithGoogleButtonClicked = {
            oneTapState.open()
            viewModel.setSignInState(it)
        }, onTokenIdReceived = { tokenId ->
            viewModel.signInWithFirebase(tokenId = tokenId, onSuccess = { token ->
                Log.d(TAG, "authenticationRoute: Token: $token")
                SecurePreferencesHelper.setToken(context, token = token)
                onNavigateToProductsViewTriggered()
            }, onFailure = {})
        }, onDialogDismissed = {})
    }
}
