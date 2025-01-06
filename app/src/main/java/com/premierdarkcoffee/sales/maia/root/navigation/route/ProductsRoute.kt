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
import com.premierdarkcoffee.sales.maia.root.feature.product.presentation.view.product.ProductsView
import com.premierdarkcoffee.sales.maia.root.feature.product.presentation.viewmodel.ProductViewModel
import com.premierdarkcoffee.sales.maia.root.navigation.ProductsRoute
import com.premierdarkcoffee.sales.maia.root.util.function.sharedViewModel
import com.premierdarkcoffee.sales.maia.root.util.helper.JwtSecurePreferencesHelper

fun NavGraphBuilder.productsRoute(
    navController: NavHostController,
    onNavigateToProductView: (String) -> Unit
) {
    composable<ProductsRoute> { backStackEntry ->
        val viewModel = backStackEntry.sharedViewModel<ProductViewModel>(navController = navController)
        val productState by viewModel.productsState.collectAsState()
        val searchProductText by viewModel.searchProductText.collectAsState()
        val groups by viewModel.groups.collectAsState()

        val context = LocalContext.current
        var token: String by remember { mutableStateOf("") }
        LaunchedEffect(Unit) {
            JwtSecurePreferencesHelper.getJwt(context)?.let {
                token = it
                viewModel.initData(it)
            }
        }

        ProductsView(
            productsState = productState,
            searchProductText = searchProductText,
            groups = groups,
            onSearchTextChange = viewModel::onSearchProductTextChange,
            clearSearchText = { viewModel.clearSearchProductText() },
            onNavigateToProductView = {
                onNavigateToProductView(it)
            }, onRefresh = { viewModel.onRefresh(token) }
        )

    }
}
