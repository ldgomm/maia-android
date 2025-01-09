package com.premierdarkcoffee.sales.maia.root.feature.settings.presentation.view.store

//
//  StoreView.kt
//  Maia
//
//  Created by José Ruiz on 31/7/24.
//

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.google.android.gms.maps.model.LatLng
import com.premierdarkcoffee.sales.maia.R
import com.premierdarkcoffee.sales.maia.root.feature.chat.domain.model.store.Store
import com.premierdarkcoffee.sales.maia.root.feature.chat.domain.model.store.StoreStatus
import com.premierdarkcoffee.sales.maia.root.feature.chat.presentation.view.chat.titleStyle
import com.premierdarkcoffee.sales.maia.root.feature.product.presentation.view.common.SectionView
import com.premierdarkcoffee.sales.maia.root.util.view.MapView

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StoreView(
    store: Store,
    onNavigateToSettingsIconButtonClicked: () -> Unit
) {

    Scaffold(topBar = {
        TopAppBar(title = { Text(text = store.name, style = titleStyle) }, actions = {
            IconButton(onClick = onNavigateToSettingsIconButtonClicked) {
                Icon(imageVector = ImageVector.vectorResource(R.drawable.settings), contentDescription = stringResource(id = R.string.settings_icon))
            }
        })
    }) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp)
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Store Image
            if (store.image.url.isNotEmpty()) {
                val painter = rememberAsyncImagePainter(model = store.image.url)
                Image(
                    painter = painter,
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp)
                        .clip(RoundedCornerShape(11.dp)),
                    contentScale = ContentScale.Crop
                )
            }

            // Address
            SectionView(title = stringResource(id = R.string.address)) {
                Text(text = "${store.address.street}, ${store.address.city}, ${store.address.state} ${store.address.zipCode}, ${store.address.country}")
            }

            // Map
            val location = LatLng(store.address.location.coordinates[1], store.address.location.coordinates[0])
            MapView(location = location)

            // Phone Number
            SectionView(title = stringResource(id = R.string.phone_number)) {
                Text(text = "📞 (+593) ${store.phoneNumber}")
            }

            // Email Address
            SectionView(title = stringResource(id = R.string.email_address)) {
                Text(text = "✉️ ${store.emailAddress}")
            }

            // Website
            SectionView(title = stringResource(id = R.string.website)) {
                Text(text = store.website)
            }

            Divider()

            // Description
            SectionView(title = stringResource(id = R.string.description)) {
                Text(text = store.description)
            }

            // Return Policy
            SectionView(title = stringResource(id = R.string.return_policy)) {
                Text(text = store.returnPolicy)
            }

            // Refund Policy
            SectionView(title = stringResource(id = R.string.refund_policy)) {
                Text(text = store.refundPolicy)
            }

            // Brands
            SectionView(title = stringResource(id = R.string.brands)) {
                Text(text = store.brands.joinToString(", "))
            }

            Divider()

            // Status
            SectionView(title = stringResource(id = R.string.status)) {
                StatusView(status = store.status)
            }
        }
    }
}

@Composable
fun StatusView(status: StoreStatus) {
    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
        if (status.isActive) StatusLabel(text = stringResource(id = R.string.active_status), color = Color.Green)
        if (status.isVerified) StatusLabel(text = stringResource(id = R.string.verified_status), color = Color.Green)
        if (status.isPromoted) StatusLabel(text = stringResource(id = R.string.promoted_status), color = Color.Blue)
        if (status.isSuspended) StatusLabel(text = stringResource(id = R.string.suspended_status), color = Color.Red)
        if (status.isClosed) StatusLabel(text = stringResource(id = R.string.closed_status), color = Color.Gray)
        if (status.isPendingApproval) StatusLabel(text = stringResource(id = R.string.pending_approval_status), color = Color.Yellow)
        if (status.isUnderReview) StatusLabel(text = stringResource(id = R.string.under_review_status), color = Color.Cyan)
        if (status.isOutOfStock) StatusLabel(text = stringResource(id = R.string.out_of_stock_status), color = Color.Black)
        if (status.isOnSale) StatusLabel(text = stringResource(id = R.string.on_sale_status), color = Color.Magenta)
    }
}

@Composable
fun StatusLabel(
    text: String,
    color: Color
) {
    Text(text = text, color = color, fontWeight = FontWeight.Bold)
}
