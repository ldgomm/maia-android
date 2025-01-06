package com.premierdarkcoffee.sales.maia.root.navigation.route

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.premierdarkcoffee.sales.maia.root.feature.settings.presentation.view.store.StoreView
import com.premierdarkcoffee.sales.maia.root.feature.settings.presentation.viewmodel.SettingsViewModel
import com.premierdarkcoffee.sales.maia.root.navigation.StoreRoute
import com.premierdarkcoffee.sales.maia.root.util.function.sharedViewModel
import com.premierdarkcoffee.sales.maia.root.util.helper.JwtSecurePreferencesHelper

fun NavGraphBuilder.storeRoute(
    navController: NavHostController,
    onNavigateToSettingsIconButtonClicked: () -> Unit
) {
    composable<StoreRoute> { backStackEntry ->
        val viewModel = backStackEntry.sharedViewModel<SettingsViewModel>(navController = navController)
        val storeState by viewModel.storeState.collectAsState()

        val context = LocalContext.current
        LaunchedEffect(Unit) {
            JwtSecurePreferencesHelper.getJwt(context)?.let {
                Log.d(TAG, "storeRoute: JWT: $it")
                viewModel.initData(it)
            }
        }

        storeState.store?.let {
            StoreView(store = it, onNavigateToSettingsIconButtonClicked)
        } ?: run {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }
    }
}
