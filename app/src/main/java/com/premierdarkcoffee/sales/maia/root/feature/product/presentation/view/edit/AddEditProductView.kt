package com.premierdarkcoffee.sales.maia.root.feature.product.presentation.view.edit

import android.content.Context
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts.PickVisualMedia
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.google.firebase.storage.FirebaseStorage
import com.premierdarkcoffee.sales.maia.R
import com.premierdarkcoffee.sales.maia.root.feature.product.domain.model.product.Image
import com.premierdarkcoffee.sales.maia.root.feature.product.domain.model.product.Offer
import com.premierdarkcoffee.sales.maia.root.feature.product.domain.model.product.Price
import com.premierdarkcoffee.sales.maia.root.feature.product.domain.model.product.Product
import com.premierdarkcoffee.sales.maia.root.feature.product.domain.state.AddEditProductState
import com.premierdarkcoffee.sales.maia.root.feature.product.domain.state.InformationResultState
import com.premierdarkcoffee.sales.maia.root.feature.product.presentation.view.common.SectionView
import com.premierdarkcoffee.sales.maia.root.util.function.shouldDeletePath
import com.premierdarkcoffee.sales.maia.root.util.function.uploadImageToFirebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditProductView(
    addEditProductState: AddEditProductState,
    informationResultStateList: List<InformationResultState>,
    product: Product,
    setName: (String) -> Unit,
    setLabel: (String) -> Unit,
    setOwner: (String) -> Unit,
    setYear: (String) -> Unit,
    setModel: (String) -> Unit,
    setDescription: (String) -> Unit,
    setPrice: (Price) -> Unit,
    setStock: (Int) -> Unit,
    setImage: (Image) -> Unit,
    addKeyword: (String) -> Unit,
    deleteKeyword: (Int) -> Unit,
    setLegal: (String?) -> Unit,
    setWarning: (String?) -> Unit,
    addProduct: (Product) -> Unit,
    updateProduct: (Product) -> Unit
) {
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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(paddingValues)
                .padding(horizontal = 16.dp)
        ) {
            // Image Section
            ImageCard(product.image.url, selectedImageUri) {
                photoPickerLauncher.launch(PickVisualMediaRequest(PickVisualMedia.ImageOnly))
            }

            // Input Fields for Product Details
            TextFieldCard(stringResource(R.string.name_label), addEditProductState.name, setName)
            TextFieldCard(stringResource(R.string.label_label), addEditProductState.label, setLabel)
            TextFieldCard(stringResource(R.string.owner_label), addEditProductState.owner, setOwner)
            TextFieldCard(stringResource(R.string.year_label), addEditProductState.year, setYear)
            TextFieldCard(stringResource(R.string.model_label), addEditProductState.model, setModel)
            TextFieldCard(stringResource(R.string.description_label), addEditProductState.description, setDescription)

            Divider(modifier = Modifier.padding(vertical = 12.dp))

            // Price Section
            TextFieldCard(
                stringResource(R.string.price_amount_label),
                addEditProductState.price.amount.toString(),
                { setPrice(addEditProductState.price.copy(amount = it.toDoubleOrNull() ?: 0.0)) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
            )

            // Offer Section
            SectionView(title = stringResource(id = R.string.offer_label)) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp), horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = stringResource(id = R.string.is_in_offer_label), style = MaterialTheme.typography.bodyLarge
                    )
                    Switch(checked = addEditProductState.price.offer.isActive, onCheckedChange = {
                        setPrice(addEditProductState.price.copy(offer = Offer(it, addEditProductState.price.offer.discount)))
                    })
                }
            }

            TextFieldCard(
                stringResource(R.string.discount_label),
                addEditProductState.price.offer.discount.toString(),
                { setPrice(addEditProductState.price.copy(offer = Offer(true, it.toIntOrNull() ?: 0))) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )

            Divider(modifier = Modifier.padding(vertical = 12.dp))

            // Stock Section
            TextFieldCard(
                stringResource(R.string.stock_label),
                addEditProductState.stock.toString(),
                { setStock(it.toIntOrNull() ?: 0) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )

            // Keyword Section
            Box(modifier = Modifier.padding(horizontal = 12.dp)) {
                KeywordSection(addEditProductState, word, { word = it }, addKeyword, deleteKeyword)
            }

            // Legal and Warning Information
            TextFieldCard(stringResource(id = R.string.legal_info_label), addEditProductState.legal.orEmpty(), setLegal)
            TextFieldCard(stringResource(id = R.string.warning_info_label), addEditProductState.warning.orEmpty(), setWarning)

            // Submit Button
            SubmitButton(
                mainImageHasChanged, addEditProductState, selectedImageUri, context, scope, setImage, addProduct, updateProduct
            )
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(title: String) {
    CenterAlignedTopAppBar(title = {
        Text(text = title, modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center)
    })
}

@Composable
fun ImageCard(
    imageUrl: String,
    selectedImageUri: Uri?,
    onClick: () -> Unit
) {
    // Localized string for accessibility
    val imageDescription = stringResource(id = R.string.image_card_description)

    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp, horizontal = 11.dp),
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.elevatedCardElevation(4.dp)
    ) {
        AsyncImage(
            model = selectedImageUri ?: imageUrl,
            contentDescription = imageDescription,
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
                .clickable { onClick() }
                .semantics { contentDescription = imageDescription },
            contentScale = ContentScale.Crop
        )
    }
}

@Composable
fun TextFieldCard(
    label: String,
    text: String,
    onTextChanged: (String) -> Unit,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    onClick: (() -> Unit)? = null
) {
    ElevatedCard(
        modifier = Modifier
            .clickable { onClick?.invoke() }
            .fillMaxWidth()
            .padding(vertical = 6.dp, horizontal = 11.dp),
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.elevatedCardElevation(4.dp)
    ) {
        TextField(
            value = text,
            onValueChange = onTextChanged,
            label = { Text(label) },
            keyboardOptions = keyboardOptions,
            modifier = Modifier
                .fillMaxWidth()
                .semantics { contentDescription = label }
        )
    }
}

@Composable
fun SubmitButton(
    mainImageHasChanged: Boolean,
    addEditProductState: AddEditProductState,
    selectedImageUri: Uri?,
    context: Context,
    scope: CoroutineScope,
    setImage: (Image) -> Unit,
    addProduct: (Product) -> Unit,
    updateProduct: (Product) -> Unit
) {
    var isProcessing by remember { mutableStateOf(false) }

    Button(
        onClick = {
            if (isProcessing) return@Button
            isProcessing = true
            val isUpdatingProduct: Boolean = addEditProductState.image.belongs
            if (!mainImageHasChanged) {
                scope.launch(Dispatchers.Main) {
                    try {
                        val product = addEditProductState.toProduct(addEditProductState.image)
                        if (isUpdatingProduct) {
                            updateProduct(product)
                        } else {
                            addProduct(product)
                        }
                    } finally {
                        isProcessing = false
                    }
                }
            } else {
                addEditProductState.image.path?.let { oldPath ->
                    if (shouldDeletePath(oldPath)) {
                        FirebaseStorage.getInstance().reference.child(oldPath).delete().addOnSuccessListener {
                            selectedImageUri?.let { uri ->
                                uploadImageToFirebase(context.contentResolver, uri = uri) { imageInfo ->
                                    scope.launch(Dispatchers.Main) {
                                        try {
                                            val image = Image(path = imageInfo.path, url = imageInfo.url, belongs = true)
                                            setImage(image)
                                            delay(1000)
                                            val product = addEditProductState.toProduct(image)
                                            if (isUpdatingProduct) {
                                                updateProduct(product)
                                            } else {
                                                addProduct(product)
                                            }
                                        } finally {
                                            isProcessing = false
                                        }
                                    }
                                }
                            } ?: run {
                                isProcessing = false
                            }
                        }
                    }
                }
            }
        }, enabled = !isProcessing
    ) {
        Text(stringResource(id = R.string.submit_label))
    }
}
