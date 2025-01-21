package com.premierdarkcoffee.sales.maia.root.feature.product.presentation.view.edit.components

//
//  SubmitButton.kt
//  Maia
//
//  Created by José Ruiz on 16/1/25.
//

import android.content.Context
import android.net.Uri
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import com.google.firebase.storage.FirebaseStorage
import com.premierdarkcoffee.sales.maia.R
import com.premierdarkcoffee.sales.maia.root.feature.product.domain.model.product.Image
import com.premierdarkcoffee.sales.maia.root.feature.product.domain.model.product.Product
import com.premierdarkcoffee.sales.maia.root.feature.product.domain.state.AddEditProductState
import com.premierdarkcoffee.sales.maia.root.util.function.shouldDeletePath
import com.premierdarkcoffee.sales.maia.root.util.function.uploadImageToFirebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun SubmitButton(mainImageHasChanged: Boolean,
                 addEditProductState: AddEditProductState,
                 selectedImageUri: Uri?,
                 context: Context,
                 scope: CoroutineScope,
                 setImage: (Image) -> Unit,
                 addProduct: (Product) -> Unit,
                 updateProduct: (Product) -> Unit) {
    var isProcessing by remember { mutableStateOf(false) }

    Button(onClick = {
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
    }, enabled = !isProcessing) {
        Text(stringResource(id = R.string.submit_label))
    }
}
