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
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.google.firebase.storage.FirebaseStorage
import com.premierdarkcoffee.sales.maia.R
import com.premierdarkcoffee.sales.maia.root.feature.product.domain.model.product.CreditCard
import com.premierdarkcoffee.sales.maia.root.feature.product.domain.model.product.Image
import com.premierdarkcoffee.sales.maia.root.feature.product.domain.model.product.Offer
import com.premierdarkcoffee.sales.maia.root.feature.product.domain.model.product.Price
import com.premierdarkcoffee.sales.maia.root.feature.product.domain.model.product.Product
import com.premierdarkcoffee.sales.maia.root.feature.product.domain.state.AddEditProductState
import com.premierdarkcoffee.sales.maia.root.feature.product.domain.state.InformationResultState
import com.premierdarkcoffee.sales.maia.root.feature.product.presentation.view.common.SectionView
import com.premierdarkcoffee.sales.maia.root.util.constant.Constant.eleven
import com.premierdarkcoffee.sales.maia.root.util.constant.Constant.four
import com.premierdarkcoffee.sales.maia.root.util.constant.Constant.six
import com.premierdarkcoffee.sales.maia.root.util.function.shouldDeletePath
import com.premierdarkcoffee.sales.maia.root.util.function.uploadImageToFirebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

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
    val scrollBehavior = rememberScrollState()

    var word by remember { mutableStateOf("") }

    Scaffold(modifier = Modifier
        .background(MaterialTheme.colorScheme.surface)
        .statusBarsPadding()
        .navigationBarsPadding(),
             topBar = { TopBar(addEditProductState.name) }) { paddingValues ->
        Column(
            Modifier
                .fillMaxSize()
                .verticalScroll(scrollBehavior)
                .padding(top = paddingValues.calculateTopPadding())
                .padding(horizontal = paddingValues.calculateLeftPadding(LayoutDirection.Ltr))
                .padding(horizontal = paddingValues.calculateLeftPadding(LayoutDirection.Rtl))
                .padding(bottom = paddingValues.calculateBottomPadding())
        ) {
            ImageCard(product.image.url, selectedImageUri) {
                photoPickerLauncher.launch(PickVisualMediaRequest(PickVisualMedia.ImageOnly))
            }

            TextFieldCard(stringResource(id = R.string.name_label), addEditProductState.name, setName)
            TextFieldCard(stringResource(id = R.string.label_label), addEditProductState.label, setLabel)
            TextFieldCard(stringResource(id = R.string.owner_label), addEditProductState.owner, setOwner)
// Year
            TextFieldCard(stringResource(id = R.string.year_label), addEditProductState.year, setYear)
            TextFieldCard(stringResource(id = R.string.model_label), addEditProductState.model, setModel)
            TextFieldCard(stringResource(id = R.string.description_label), addEditProductState.description, setDescription)

            Box(
                Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp)
            )
// Price
            Divider()
            TextFieldCard(
                stringResource(id = R.string.price_amount_label), addEditProductState.price.amount.toString(), { amount ->
                    setPrice(addEditProductState.price.copy(amount = amount.toDoubleOrNull() ?: 0.0))
                }, KeyboardOptions(keyboardType = KeyboardType.Decimal)
            )

            SectionView(title = stringResource(id = R.string.offer_label)) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(id = R.string.is_in_offer_label),
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                    Switch(checked = addEditProductState.price.offer.isActive, onCheckedChange = {
                        setPrice(
                            addEditProductState.price.copy(
                                offer = Offer(
                                    isActive = it, discount = addEditProductState.price.offer.discount
                                )
                            )
                        )
                    })
                }
            }

            TextFieldCard(
                stringResource(id = R.string.discount_label), addEditProductState.price.offer.discount.toString(), { discount ->
                    setPrice(
                        addEditProductState.price.copy(
                            offer = Offer(
                                addEditProductState.price.offer.isActive, discount = discount.toIntOrNull() ?: 0
                            )
                        )
                    )
                }, KeyboardOptions(keyboardType = KeyboardType.Number)
            )

            TextFieldCard(
                stringResource(id = R.string.without_interest_label), addEditProductState.price.creditCard?.withoutInterest.toString(), { withoutInterest ->
                    setPrice(
                        addEditProductState.price.copy(
                            creditCard = CreditCard(
                                withoutInterest = withoutInterest.toIntOrNull() ?: 0,
                                withInterest = addEditProductState.price.creditCard?.withInterest ?: 0,
                                freeMonths = addEditProductState.price.creditCard?.freeMonths ?: 0
                            )
                        )
                    )
                }, KeyboardOptions(keyboardType = KeyboardType.Number)
            )
            TextFieldCard(
                stringResource(id = R.string.with_interest_label), addEditProductState.price.creditCard?.withInterest.toString(), { withInterest ->
                    setPrice(
                        addEditProductState.price.copy(
                            creditCard = CreditCard(
                                withoutInterest = addEditProductState.price.creditCard?.withoutInterest ?: 0,
                                withInterest = withInterest.toIntOrNull() ?: 0,
                                freeMonths = addEditProductState.price.creditCard?.freeMonths ?: 0
                            )
                        )
                    )
                }, KeyboardOptions(keyboardType = KeyboardType.Number)
            )
            TextFieldCard(
                stringResource(id = R.string.free_months_label), addEditProductState.price.creditCard?.freeMonths.toString(), { freeMonths ->
                    setPrice(
                        addEditProductState.price.copy(
                            creditCard = CreditCard(
                                withoutInterest = addEditProductState.price.creditCard?.withoutInterest ?: 0,
                                withInterest = addEditProductState.price.creditCard?.withInterest ?: 0,
                                freeMonths = freeMonths.toIntOrNull() ?: 0
                            )
                        )
                    )
                }, KeyboardOptions(keyboardType = KeyboardType.Number)
            )
            Divider()
            Box(
                Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp)
            )

// Stock
            TextFieldCard(stringResource(id = R.string.stock_label), addEditProductState.stock.toString(), { stock ->
                setStock(stock.toIntOrNull() ?: 0)
            }, KeyboardOptions(keyboardType = KeyboardType.Number))

// Overview
            InformationListView(informationResultStateList)
//
            Box(modifier = Modifier.padding(horizontal = 12.dp)) {
                KeywordSection(
                    addEditProductState = addEditProductState, word = word, onWordChange = { word = it }, addKeyword = addKeyword, deleteKeyword = deleteKeyword
                )
            }

// Legal
            TextFieldCard(stringResource(id = R.string.legal_info_label), addEditProductState.legal.orEmpty(), setLegal)

// Warning
            TextFieldCard(stringResource(id = R.string.warning_info_label), addEditProductState.warning.orEmpty(), setWarning)

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
    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = six)
            .padding(horizontal = eleven),
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.elevatedCardElevation(four)
    ) {
        AsyncImage(
            model = selectedImageUri ?: imageUrl,
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
                .clickable { onClick() },
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
    ElevatedCard(modifier = Modifier
        .clickable { onClick?.invoke() }
        .fillMaxWidth()
        .padding(vertical = six)
        .padding(horizontal = eleven),
                 shape = MaterialTheme.shapes.medium,
                 elevation = CardDefaults.elevatedCardElevation(four)) {
        TextField(
            value = text, onValueChange = onTextChanged, label = { Text(label) }, keyboardOptions = keyboardOptions, modifier = Modifier.fillMaxWidth()
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


@Composable
fun KeywordSection(
    addEditProductState: AddEditProductState,
    word: String,
    onWordChange: (String) -> Unit,
    addKeyword: (String) -> Unit,
    deleteKeyword: (Int) -> Unit
) {
    Column {
        // Header
        Text(text = "Keywords", style = MaterialTheme.typography.titleMedium, modifier = Modifier.padding(bottom = 8.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        ) {
            TextField(
                value = word,
                onValueChange = onWordChange,
                label = { Text("Please enter only the main words") },
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 8.dp)
            )

            Button(
                onClick = {
                    addKeyword(word)
                    onWordChange("")
                }, enabled = word.isNotEmpty(), modifier = Modifier.align(Alignment.CenterVertically)
            ) {
                Text("Add")
            }
        }

        LazyRow(modifier = Modifier.fillMaxWidth()) {
            items(addEditProductState.keywords) { keyword ->
                val index = addEditProductState.keywords.indexOf(keyword)
                KeywordBubble(keyword = keyword) {
                    deleteKeyword(index)
                }
            }
        }

        // Footer
        Text(
            text = "Please add only the main words that describe your product",
            style = MaterialTheme.typography.labelSmall,
            modifier = Modifier.padding(top = 8.dp)
        )
    }
}
