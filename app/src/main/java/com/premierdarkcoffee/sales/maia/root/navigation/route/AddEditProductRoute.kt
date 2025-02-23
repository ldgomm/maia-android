package com.premierdarkcoffee.sales.maia.root.navigation.route

import android.content.ContentValues.TAG
import android.util.Log
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
import androidx.navigation.toRoute
import com.google.gson.Gson
import com.premierdarkcoffee.sales.maia.root.feature.product.data.remote.dto.ProductDto
import com.premierdarkcoffee.sales.maia.root.feature.product.presentation.view.product.edit.AddEditProductView
import com.premierdarkcoffee.sales.maia.root.feature.product.presentation.viewmodel.ProductViewModel
import com.premierdarkcoffee.sales.maia.root.navigation.AddEditProductRoute
import com.premierdarkcoffee.sales.maia.root.util.function.sharedViewModel
import com.premierdarkcoffee.sales.maia.root.util.helper.JwtSecurePreferencesHelper

fun NavGraphBuilder.addEditProductRoute(navController: NavHostController, onBackToProductsActionTriggered: () -> Unit) {
    composable<AddEditProductRoute> { backStackEntry ->
        val viewModel = backStackEntry.sharedViewModel<ProductViewModel>(navController = navController)
        val args = backStackEntry.toRoute<AddEditProductRoute>()
        val product = Gson().fromJson(args.product, ProductDto::class.java).toProduct()

        val addOrUpdateProductState by viewModel.addEditProductState.collectAsState()

        // Use a State to capture the token asynchronously
        var token: String? by remember { mutableStateOf(null) }
        val context = LocalContext.current

        LaunchedEffect(Unit) {
            JwtSecurePreferencesHelper.getJwt(context)?.let {
                token = it
                viewModel.initData(it)
            }
        }

        LaunchedEffect(product) {
            viewModel.setProduct(product)
        }

        AddEditProductView(addEditProductState = addOrUpdateProductState,
                           product = product,
                           setName = viewModel::setName,
                           setLabel = viewModel::setLabel,
                           setOwner = viewModel::setOwner,
                           setYear = viewModel::setYear,
                           setModel = viewModel::setModel,
                           setDescription = viewModel::setDescription,
                // category
                           setPrice = viewModel::setPrice,
                           setStock = viewModel::setStock,
                           setImage = viewModel::setImage,
                // origin
                // date
                // overview
                // keywords
                           addKeyword = viewModel::addKeyword,
                           deleteKeyword = viewModel::deleteKeyword,
                // codes
                // specifications
                // warranty
                           setWarranty = viewModel::setWarranty,
                           setLegal = viewModel::setLegal,
                           setWarning = viewModel::setWarning,
                           addProduct = { value ->
                               token?.let { jwt: String ->
                                   Log.d(TAG, "addEditProductRoute: $jwt")
                                   viewModel.addProduct(product = value,
                                                        token = jwt,
                                                        onSuccess = { onBackToProductsActionTriggered() },
                                                        onFailure = {})
                               } ?: Log.d(TAG, "addEditProductRoute: Token not available")
                           },
                           updateProduct = { value ->
                               token?.let { jwt: String ->
                                   Log.d(TAG, "addEditProductRoute: $jwt")
                                   viewModel.updateProduct(product = value,
                                                           token = jwt,
                                                           onSuccess = { onBackToProductsActionTriggered() },
                                                           onFailure = {})
                               } ?: Log.d(TAG, "addEditProductRoute: Token not available")
                           })
    }

}
