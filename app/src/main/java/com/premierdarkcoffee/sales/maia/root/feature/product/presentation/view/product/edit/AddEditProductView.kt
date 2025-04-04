package com.premierdarkcoffee.sales.maia.root.feature.product.presentation.view.product.edit

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts.PickVisualMedia
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.premierdarkcoffee.sales.maia.R
import com.premierdarkcoffee.sales.maia.root.feature.product.domain.model.product.Image
import com.premierdarkcoffee.sales.maia.root.feature.product.domain.model.product.Offer
import com.premierdarkcoffee.sales.maia.root.feature.product.domain.model.product.Price
import com.premierdarkcoffee.sales.maia.root.feature.product.domain.model.product.Product
import com.premierdarkcoffee.sales.maia.root.feature.product.domain.state.AddEditProductState
import com.premierdarkcoffee.sales.maia.root.feature.product.presentation.view.common.SectionView
import com.premierdarkcoffee.sales.maia.root.feature.product.presentation.view.product.edit.components.ImageCard
import com.premierdarkcoffee.sales.maia.root.feature.product.presentation.view.product.edit.components.IntTextFieldCardWithStepper
import com.premierdarkcoffee.sales.maia.root.feature.product.presentation.view.product.edit.components.SmartDoubleTextFieldCard
import com.premierdarkcoffee.sales.maia.root.feature.product.presentation.view.product.edit.components.SubmitButton
import com.premierdarkcoffee.sales.maia.root.feature.product.presentation.view.product.edit.components.TextFieldCard
import com.premierdarkcoffee.sales.maia.root.feature.product.presentation.view.product.edit.components.TopBar

@Composable
fun AddEditProductView(addEditProductState: AddEditProductState,
                       product: Product,
                       setName: (String) -> Unit,
                       setLabel: (String) -> Unit,
                       setYear: (String) -> Unit,
                       setDescription: (String) -> Unit,
                       setPrice: (Price) -> Unit,
                       setStock: (Int) -> Unit,
                       setImage: (Image) -> Unit,
                       addKeyword: (String) -> Unit,
                       deleteKeyword: (Int) -> Unit,
                       setWarranty: (String?) -> Unit,
                       setLegal: (String?) -> Unit,
                       setWarning: (String?) -> Unit,
                       addProduct: (Product) -> Unit,
                       updateProduct: (Product) -> Unit) {
    val context = LocalContext.current
    var mainImageHasChanged by remember { mutableStateOf(false) }
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }

    val photoPickerLauncher = rememberLauncherForActivityResult(contract = PickVisualMedia()) {
        selectedImageUri = it
        mainImageHasChanged = true
    }

    val scope = rememberCoroutineScope()
    val scrollState = rememberScrollState()
    var word by remember { mutableStateOf("") }

    Scaffold(modifier = Modifier
        .background(MaterialTheme.colorScheme.surface)
        .statusBarsPadding()
        .navigationBarsPadding(),
             topBar = { TopBar(addEditProductState.name) }) { paddingValues ->
        Column(modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(paddingValues)) {
            // Image Section
            ImageCard(product.image.url, selectedImageUri) {
                photoPickerLauncher.launch(PickVisualMediaRequest(PickVisualMedia.ImageOnly))
            }

            // Input Fields for Product Details
            TextFieldCard(stringResource(R.string.name_label), addEditProductState.name, setName)
            TextFieldCard(stringResource(R.string.label_label), addEditProductState.label, setLabel)
//            TextFieldCard(stringResource(R.string.owner_label),
//                          addEditProductState.owner,
//                          setOwner,
//                          readOnly = true,
//                          enabled = false)
            TextFieldCard(stringResource(R.string.year_label), addEditProductState.year, setYear)
//            TextFieldCard(stringResource(R.string.model_label),
//                          addEditProductState.model,
//                          setModel,
//                          readOnly = true,
//                          enabled = false)
            TextFieldCard(stringResource(R.string.description_label), addEditProductState.description, setDescription)

            Divider(modifier = Modifier.padding(vertical = 12.dp))

            // Price Section
            SmartDoubleTextFieldCard(label = stringResource(R.string.price_amount_label),
                                     value = addEditProductState.price.amount, // This is your Double
                                     onValueChange = { newAmount ->
                                         setPrice(addEditProductState.price.copy(amount = newAmount))
                                     },
                                     keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal))

            // Offer Section
            SectionView(title = stringResource(id = R.string.offer_label)) {
                Column {
                    Row(horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp)) {
                        Text(text = stringResource(id = R.string.is_in_offer_label), style = MaterialTheme.typography.bodyLarge)
                        Switch(checked = addEditProductState.price.offer.isActive, onCheckedChange = {
                            setPrice(addEditProductState.price.copy(offer = Offer(it, addEditProductState.price.offer.discount)))
                        })
                    }

                    AnimatedVisibility(addEditProductState.price.offer.isActive) {
                        IntTextFieldCardWithStepper(label = stringResource(R.string.discount_label),
                                                    value = addEditProductState.price.offer.discount,
                                                    onValueChange = { newDiscount ->
                                                        setPrice(addEditProductState.price.copy(offer = Offer(true, newDiscount)))
                                                    },
                                                    valueRange = 0..50)
                    }
                }
            }
            Divider(modifier = Modifier.padding(vertical = 12.dp))

            // Stock Section
            IntTextFieldCardWithStepper(label = stringResource(R.string.stock_label),
                                        value = addEditProductState.stock,
                                        onValueChange = { setStock(it) },
                                        valueRange = 0..100)

            // Keyword Section
            Box(modifier = Modifier.padding(horizontal = 12.dp)) {
                KeywordSection(addEditProductState, word, { word = it }, addKeyword, deleteKeyword)
            }

            // Legal and Warning Information
            TextFieldCard(stringResource(id = R.string.warranty_info_label), addEditProductState.warranty.orEmpty(), setWarranty)
            TextFieldCard(stringResource(id = R.string.legal_info_label), addEditProductState.legal.orEmpty(), setLegal)
            TextFieldCard(stringResource(id = R.string.warning_info_label), addEditProductState.warning.orEmpty(), setWarning)

            // Submit Button
            SubmitButton(mainImageHasChanged,
                         addEditProductState,
                         selectedImageUri,
                         context,
                         scope,
                         setImage,
                         addProduct,
                         updateProduct)
        }
    }
}

