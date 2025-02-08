package com.premierdarkcoffee.sales.maia.root.feature.chat.presentation.view.chat.component

//
//  ProductItemView.kt
//  Maia
//
//  Created by José Ruiz on 14/7/24.
//

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.google.gson.Gson
import com.premierdarkcoffee.sales.maia.R
import com.premierdarkcoffee.sales.maia.root.feature.product.domain.model.product.Product
import java.text.NumberFormat
import java.util.Currency

@Composable
fun ProductItemView(product: Product, onNavigateToProductView: (String) -> Unit) {
    // Localized strings for accessibility and internationalization
    val discountLabel = stringResource(id = R.string.discount_label)
    val originalPriceLabel = stringResource(id = R.string.original_price_label)
    val currentPriceLabel = stringResource(id = R.string.current_price_label)
    val currency = Currency.getInstance(product.price.currency)

    val numberFormat = NumberFormat.getCurrencyInstance().apply {
        this.currency = currency
    }

    val discountPrice = product.price.amount * (1 - product.price.offer.discount / 100.0)

    ElevatedCard(onClick = { onNavigateToProductView(Gson().toJson(product)) },
                 modifier = Modifier
                     .fillMaxWidth()
                     .padding(vertical = 8.dp, horizontal = 16.dp)
                     .semantics {
                         contentDescription = "${product.name}, $currentPriceLabel ${numberFormat.format(product.price.amount)}"
                     },
                 shape = RoundedCornerShape(16.dp),
                 elevation = CardDefaults.elevatedCardElevation(8.dp)) {
        Row(modifier = Modifier
            .padding(12.dp)
            .fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            // Product Image with accessibility support
            AsyncImage(model = product.image.url,
                       contentDescription = "${product.name} - ${product.model}",
                       modifier = Modifier
                           .size(72.dp)
                           .clip(RoundedCornerShape(8.dp))
                           .background(MaterialTheme.colorScheme.surfaceVariant)
                           .semantics { contentDescription = "${product.name} image" },
                       contentScale = ContentScale.Crop)

            Spacer(modifier = Modifier.width(16.dp))

            // Product Name and Model
            Column(verticalArrangement = Arrangement.Center, modifier = Modifier.weight(1f)) {
                Text(text = product.name,
                     style = MaterialTheme.typography.titleMedium,
                     fontWeight = FontWeight.Bold,
                     color = MaterialTheme.colorScheme.onSurface,
                     modifier = Modifier.padding(bottom = 4.dp))
                Text(text = product.model,
                     style = MaterialTheme.typography.bodySmall,
                     fontWeight = FontWeight.Medium,
                     color = MaterialTheme.colorScheme.onSurface)
            }

            // Pricing Section
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                if (product.price.offer.isActive) {
                    if (product.price.offer.isActive) {
                        Text(text = "${product.price.offer.discount}% OFF",
                             style = TextStyle(fontSize = 10.sp),
                             color = Color.White,
                             fontWeight = FontWeight.Bold,
                             modifier = Modifier
                                 .clip(RoundedCornerShape(4.dp))
                                 .background(Color.Red.copy(alpha = 0.7f))
                                 .padding(horizontal = 4.dp, vertical = 2.dp)
                                 .semantics { contentDescription = "${product.price.offer.discount} percent off" })
                    }
                    Text(text = numberFormat.format(discountPrice),
                         style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold),
                         color = MaterialTheme.colorScheme.primary,
                         modifier = Modifier.padding(top = 4.dp))
                    Text(text = numberFormat.format(product.price.amount),
                         style = MaterialTheme.typography.bodySmall.copy(textDecoration = TextDecoration.LineThrough),
                         color = MaterialTheme.colorScheme.onSurfaceVariant,
                         modifier = Modifier.padding(top = 2.dp))
                } else {
                    Text(text = numberFormat.format(product.price.amount),
                         style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold),
                         color = MaterialTheme.colorScheme.primary,
                         modifier = Modifier.padding(top = 4.dp))
                }
            }
        }
    }
}
