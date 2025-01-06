package com.premierdarkcoffee.sales.maia.root.navigation.route

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.premierdarkcoffee.sales.maia.root.feature.product.presentation.view.search.SearchView
import com.premierdarkcoffee.sales.maia.root.feature.product.presentation.viewmodel.ProductViewModel
import com.premierdarkcoffee.sales.maia.root.navigation.SearchRoute
import com.premierdarkcoffee.sales.maia.root.util.function.sharedViewModel
import com.premierdarkcoffee.sales.maia.root.util.helper.JwtSecurePreferencesHelper

fun NavGraphBuilder.searchRoute(
    navController: NavHostController,
    onNavigateToProductView: (String) -> Unit
) {
    composable<SearchRoute> { backStackEntry ->
        val viewModel = backStackEntry.sharedViewModel<ProductViewModel>(navController = navController)

        val productsState by viewModel.searchState.collectAsState()
        val searchText by viewModel.searchText.collectAsState()

        var token: String by remember { mutableStateOf("") }
        val context = LocalContext.current
        LaunchedEffect(Unit) {
            JwtSecurePreferencesHelper.getJwt(context)?.let {
                token = it
                viewModel.initData(it)
            }
        }

        SearchView(
            productsState = productsState,
            searchText = searchText,
            onSearchTextChange = viewModel::onSearchTextChange,
            clearSearchText = { viewModel.clearSearchText() },
            onNavigateToProductView = onNavigateToProductView
        )
    }
}
