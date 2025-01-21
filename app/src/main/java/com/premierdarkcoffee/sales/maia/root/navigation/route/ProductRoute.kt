package com.premierdarkcoffee.sales.maia.root.navigation.route

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.google.gson.Gson
import com.premierdarkcoffee.sales.maia.root.feature.product.data.remote.dto.ProductDto
import com.premierdarkcoffee.sales.maia.root.feature.product.presentation.view.product.ProductView
import com.premierdarkcoffee.sales.maia.root.feature.product.presentation.viewmodel.ProductViewModel
import com.premierdarkcoffee.sales.maia.root.navigation.ProductRoute
import com.premierdarkcoffee.sales.maia.root.util.function.sharedViewModel

fun NavGraphBuilder.productRoute(navController: NavHostController,
                                 onPopBackStackActionTriggered: () -> Unit,
                                 onAddOrUpdateEditedProductButtonClick: (String) -> Unit) {
    composable<ProductRoute> { backStackEntry ->
        val viewModel = backStackEntry.sharedViewModel<ProductViewModel>(navController = navController)
        val args = backStackEntry.toRoute<ProductRoute>()
        val product = Gson().fromJson(args.product, ProductDto::class.java).toProduct()

        ProductView(product = product,
                    onPopBackStackActionTriggered = onPopBackStackActionTriggered,
                    onAddOrUpdateEditedProductButtonClick = onAddOrUpdateEditedProductButtonClick)
    }
}
