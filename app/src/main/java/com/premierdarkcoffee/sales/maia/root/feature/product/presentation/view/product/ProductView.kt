package com.premierdarkcoffee.sales.maia.root.feature.product.presentation.view.product

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.sharp.Edit
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.google.gson.Gson
import com.premierdarkcoffee.sales.maia.R
import com.premierdarkcoffee.sales.maia.root.feature.product.domain.model.product.Product
import com.premierdarkcoffee.sales.maia.root.feature.product.presentation.view.common.SectionView

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductView(
    product: Product,
    onPopBackStackActionTriggered: () -> Unit,
    onAddOrUpdateEditedProductButtonClick: (String) -> Unit
) {
    val scrollState = rememberScrollState()

    // Localized strings for accessibility and screen readers
    val backDescription = stringResource(id = R.string.back_button)
    val editDescription = stringResource(id = R.string.edit_button)
    val noLabelAvailable = stringResource(id = R.string.no_label_available)

    Scaffold(topBar = {
        TopAppBar(title = {
            Text(
                text = product.name, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold
            )
        }, navigationIcon = {
            IconButton(onClick = onPopBackStackActionTriggered) {
                Icon(
                    imageVector = Icons.Default.ArrowBack, contentDescription = backDescription
                )
            }
        })
    }, floatingActionButton = {
        FloatingActionButton(
            onClick = { onAddOrUpdateEditedProductButtonClick(Gson().toJson(product)) },
            containerColor = MaterialTheme.colorScheme.secondary,
            contentColor = Color.White
        ) {
            Icon(
                imageVector = Icons.Sharp.Edit, contentDescription = editDescription
            )
        }
    }) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(paddingValues)
        ) {
            // Image Section with accessibility support
            Box(
                contentAlignment = Alignment.BottomEnd, modifier = Modifier
                    .fillMaxWidth()
                    .height(400.dp)
            ) {
                AsyncImage(
                    model = product.image.url,
                    contentDescription = stringResource(id = R.string.product_image_description, product.name),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(400.dp),
                    contentScale = ContentScale.Fit
                )

                Text(
                    text = product.category.subclass,
                    style = MaterialTheme.typography.labelSmall,
                    color = Color.White,
                    modifier = Modifier
                        .padding(end = 16.dp, bottom = 12.dp)
                        .background(Color.Gray.copy(alpha = 0.7f), RoundedCornerShape(10.dp))
                        .padding(horizontal = 10.dp, vertical = 6.dp)
                        .align(Alignment.BottomEnd)
                )
            }

            // Product Information Sections
            SectionView(title = stringResource(id = R.string.label_label)) {
                Text(
                    text = product.label ?: stringResource(id = R.string.no_label_available),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSecondaryContainer,
                    modifier = Modifier.semantics { contentDescription = product.label ?: noLabelAvailable }
                )
            }

            // Owner and Year Section
            SectionView(title = stringResource(id = R.string.owner_label)) {
                Text(
                    text = if (product.year != null) "${product.owner}, ${product.year}" else product.owner ?: "",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSecondaryContainer,
                    modifier = Modifier.semantics { contentDescription = "${product.owner}, ${product.year ?: ""}" }
                )
            }

            // Description Section
            SectionView(title = stringResource(id = R.string.description_label)) {
                Text(
                    text = product.description,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSecondaryContainer,
                    modifier = Modifier.semantics { contentDescription = product.description }
                )
            }

            // Overview Section with Cards
            if (product.overview.isNotEmpty()) {
                LazyRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                ) {
                    items(product.overview) { info ->
                        ElevatedCard(
                            modifier = Modifier
                                .width(300.dp)
                                .padding(end = 16.dp),
                            shape = MaterialTheme.shapes.medium
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                AsyncImage(
                                    model = info.image.url,
                                    contentDescription = info.title,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(180.dp)
                                        .clip(MaterialTheme.shapes.medium),
                                    contentScale = ContentScale.Crop
                                )
                                Text(
                                    text = info.title,
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.semantics { contentDescription = info.title }
                                )
                                Text(
                                    text = info.subtitle,
                                    style = MaterialTheme.typography.bodyMedium,
                                    modifier = Modifier.semantics { contentDescription = info.subtitle }
                                )
                                Text(
                                    text = info.description,
                                    style = MaterialTheme.typography.bodySmall,
                                    maxLines = 3,
                                    overflow = TextOverflow.Ellipsis,
                                    modifier = Modifier.semantics { contentDescription = info.description }
                                )
                            }
                        }
                    }
                }
            }


            // Product Details Section
            SectionView(title = stringResource(id = R.string.details_label)) {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    ProductDetailRow(
                        label = stringResource(id = R.string.price_label),
                        value = "${product.price.amount} ${product.price.currency}"
                    )
                    ProductDetailRow(
                        label = stringResource(id = R.string.stock_label),
                        value = "${product.stock}"
                    )
                    ProductDetailRow(
                        label = stringResource(id = R.string.origin_label),
                        value = product.origin
                    )
                }
            }

            // Product Specifications Section
            product.specifications?.let { specifications ->
                SectionView(title = stringResource(id = R.string.specifications_label)) {
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        ProductDetailRow(
                            label = stringResource(id = R.string.colours_label),
                            value = specifications.colours.joinToString(", ")
                        )
                        specifications.finished?.let { finished ->
                            ProductDetailRow(
                                label = stringResource(id = R.string.finished_label),
                                value = finished
                            )
                        }
                        specifications.inBox?.let { inBox ->
                            ProductDetailRow(
                                label = stringResource(id = R.string.in_box_label),
                                value = inBox.joinToString(", ")
                            )
                        }
                        specifications.size?.let { size ->
                            ProductDetailRow(
                                label = stringResource(id = R.string.size_label),
                                value = "${size.width}x${size.height}x${size.depth} ${size.unit}"
                            )
                        }
                        specifications.weight?.let { weight ->
                            ProductDetailRow(
                                label = stringResource(id = R.string.weight_label),
                                value = "${weight.weight} ${weight.unit}"
                            )
                        }
                    }
                }
            }

            // Keywords Section
            product.keywords?.let { keywords ->
                SectionView(title = stringResource(id = R.string.keywords_label)) {
                    Text(
                        text = keywords.joinToString(", "),
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.semantics { contentDescription = keywords.joinToString(", ") }
                    )
                }
            }

            // Warranty Section
            product.warranty?.let { warranty ->
                SectionView(title = stringResource(id = R.string.warranty_label)) {
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        ProductDetailRow(
                            label = stringResource(id = R.string.warranty_duration_label, warranty.months),
                            value = warranty.details.joinToString(", ")
                        )
                    }
                }
            }

            // Legal Information Section
            product.legal?.let { legal ->
                SectionView(title = stringResource(id = R.string.legal_label)) {
                    Text(
                        text = legal,
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.semantics { contentDescription = legal }
                    )
                }
            }

            // Warning Section
            product.warning?.let { warning ->
                SectionView(title = stringResource(id = R.string.warning_label)) {
                    Text(
                        text = warning,
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.semantics { contentDescription = warning }
                    )
                }
            }

        }
    }
}

@Composable
fun ProductDetailRow(
    label: String,
    value: String
) {
    Row(
        modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label, style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold)
        )
        Text(
            text = value, style = MaterialTheme.typography.bodySmall
        )
    }
}
