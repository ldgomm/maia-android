package com.premierdarkcoffee.sales.maia.root.navigation.route

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.premierdarkcoffee.sales.maia.root.feature.product.presentation.view.search.SearchView
import com.premierdarkcoffee.sales.maia.root.feature.product.presentation.viewmodel.ProductViewModel
import com.premierdarkcoffee.sales.maia.root.navigation.SearchRoute
import com.premierdarkcoffee.sales.maia.root.util.function.sharedViewModel
import com.premierdarkcoffee.sales.maia.root.util.helper.SecurePreferencesHelper

fun NavGraphBuilder.searchRoute(
    navController: NavHostController,
    onNavigateToProductView: (String) -> Unit
) {
    composable<SearchRoute> { backStackEntry ->
        val viewModel = backStackEntry.sharedViewModel<ProductViewModel>(navController = navController)

        val productsState by viewModel.searchState.collectAsState()
        val searchText by viewModel.searchText.collectAsState()

        var token: String = ""
        val context = LocalContext.current
        LaunchedEffect(Unit) {
            SecurePreferencesHelper.getToken(context)?.let {
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
