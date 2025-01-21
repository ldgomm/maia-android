package com.premierdarkcoffee.sales.maia.root.feature.product.presentation.view.product

//
//  ProductCardView.kt
//  Maia
//
//  Created by José Ruiz on 30/7/24.
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.google.gson.Gson
import com.premierdarkcoffee.sales.maia.R
import com.premierdarkcoffee.sales.maia.root.feature.product.domain.model.product.Product
import com.premierdarkcoffee.sales.maia.root.util.extension.formatDate
import java.text.NumberFormat
import java.util.Currency

@Composable
fun ProductCardView(product: Product, onNavigateToProductView: (String) -> Unit) {
    val numberFormat = NumberFormat.getCurrencyInstance().apply {
        currency = Currency.getInstance(product.price.currency)
    }
    val originalPrice = product.price.amount / (1 - product.price.offer.discount / 100.0)

    // Localized strings for accessibility and dynamic labels
    val discountLabel = stringResource(id = R.string.discount_label, product.price.offer.discount)
    val currentPriceLabel = stringResource(id = R.string.current_price_label)

    ElevatedCard(onClick = { onNavigateToProductView(Gson().toJson(product)) },
                 modifier = Modifier
                     .fillMaxWidth()
                     .padding(vertical = 4.dp)
                     .semantics {
                         contentDescription = "${product.name}, $currentPriceLabel ${numberFormat.format(product.price.amount)}"
                     },
                 shape = RoundedCornerShape(12.dp),
                 elevation = CardDefaults.elevatedCardElevation(2.dp)) {
        Row(modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            // Product Image with accessibility support
            AsyncImage(model = product.image.url,
                       contentDescription = stringResource(id = R.string.product_image_description, product.name),
                       modifier = Modifier
                           .size(72.dp)
                           .clip(RoundedCornerShape(8.dp))
                           .background(MaterialTheme.colorScheme.surfaceVariant),
                       contentScale = ContentScale.Crop)

            Spacer(modifier = Modifier.width(16.dp))

            // Product Info Section
            Column(verticalArrangement = Arrangement.Center, modifier = Modifier.weight(1f)) {
                Text(text = product.name,
                     style = MaterialTheme.typography.titleMedium,
                     fontWeight = FontWeight.Bold,
                     color = MaterialTheme.colorScheme.onSurface)
                product.label?.let {
                    Text(text = it,
                         style = MaterialTheme.typography.bodySmall,
                         fontWeight = FontWeight.Bold,
                         color = MaterialTheme.colorScheme.onSurface)
                }
                Text(text = product.description,
                     style = MaterialTheme.typography.bodySmall,
                     color = MaterialTheme.colorScheme.onSurface,
                     maxLines = 2,
                     overflow = TextOverflow.Ellipsis)
                Text(text = product.date.formatDate(),
                     style = MaterialTheme.typography.labelSmall,
                     color = MaterialTheme.colorScheme.onSurfaceVariant,
                     modifier = Modifier.padding(top = 4.dp))
            }

            // Pricing Section
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                if (product.price.offer.isActive) {
                    Text(text = discountLabel,
                         style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold),
                         color = Color.White,
                         modifier = Modifier
                             .clip(RoundedCornerShape(4.dp))
                             .background(Color.Red.copy(alpha = 0.7f))
                             .padding(horizontal = 4.dp, vertical = 2.dp)
                             .semantics { contentDescription = discountLabel })
                }
                Text(text = numberFormat.format(product.price.amount),
                     style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold),
                     color = MaterialTheme.colorScheme.primary,
                     modifier = Modifier.padding(top = 4.dp))
                Text(text = numberFormat.format(originalPrice),
                     style = MaterialTheme.typography.bodySmall.copy(textDecoration = TextDecoration.LineThrough),
                     color = MaterialTheme.colorScheme.onSurfaceVariant,
                     modifier = Modifier.padding(top = 2.dp))
            }
        }
    }
}
