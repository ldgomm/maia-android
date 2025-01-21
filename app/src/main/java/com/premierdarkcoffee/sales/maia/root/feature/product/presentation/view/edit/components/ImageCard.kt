package com.premierdarkcoffee.sales.maia.root.feature.product.presentation.view.edit.components

//
//  ImageCard.kt
//  Maia
//
//  Created by José Ruiz on 16/1/25.
//

import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.premierdarkcoffee.sales.maia.R

@Composable
fun ImageCard(imageUrl: String, selectedImageUri: Uri?, onClick: () -> Unit) {
    // Localized string for accessibility
    val imageDescription = stringResource(id = R.string.image_card_description)

    ElevatedCard(modifier = Modifier
        .fillMaxWidth()
        .padding(vertical = 6.dp, horizontal = 11.dp),
                 shape = MaterialTheme.shapes.medium,
                 elevation = CardDefaults.elevatedCardElevation(4.dp)) {
        AsyncImage(model = selectedImageUri ?: imageUrl,
                   contentDescription = imageDescription,
                   modifier = Modifier
                       .fillMaxWidth()
                       .height(150.dp)
                       .clickable { onClick() }
                       .semantics { contentDescription = imageDescription },
                   contentScale = ContentScale.Crop)
    }
}
