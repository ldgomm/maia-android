package com.premierdarkcoffee.sales.maia.root.feature.chat.presentation.view.chat.component

//
//  ClientMessageItemView.kt
//  Hermes
//
//  Created by José Ruiz on 13/7/24.
//

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.premierdarkcoffee.sales.maia.R
import com.premierdarkcoffee.sales.maia.root.feature.chat.data.local.entity.message.MessageEntity
import com.premierdarkcoffee.sales.maia.root.feature.chat.domain.model.message.Message
import com.premierdarkcoffee.sales.maia.root.feature.chat.domain.model.message.MessageStatus
import com.premierdarkcoffee.sales.maia.root.feature.chat.domain.model.message.MessageType
import com.premierdarkcoffee.sales.maia.root.feature.product.domain.model.product.Product

@Composable
fun ClientMessageItemView(message: Message,
                          product: Product? = null,
                          markMessageAsReadLaunchedEffect: (MessageEntity) -> Unit,
                          onNavigateToProductView: (String) -> Unit) {
    // State to control the expanded/collapsed state of the product details
    val expanded by remember { mutableStateOf(true) }
    val context = LocalContext.current

    // Accessibility friendly localized strings
    val messageStatusRead = stringResource(id = R.string.message_status_read)
    val messageDateLabel = stringResource(id = R.string.message_date)

    // Launch effect for marking the message as read
    LaunchedEffect(key1 = message.status) {
        if (message.status != MessageStatus.READ) {
            Log.d("ClientMessageItemView", "Message status: ${message.status}")
            markMessageAsReadLaunchedEffect(message.toMessageEntity())
        }
    }

    when (message.type) {
        MessageType.TEXT -> {
            Column(modifier = Modifier
                .fillMaxWidth()
                .padding(start = 12.dp),
                   verticalArrangement = Arrangement.Center,
                   horizontalAlignment = Alignment.Start) {
                // Message Row with text and date
                Row(modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .padding(start = 8.dp)
                    .wrapContentWidth(Alignment.Start)
                    .padding(vertical = 4.dp),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically) {
                    Column(verticalArrangement = Arrangement.spacedBy(2.dp),
                           horizontalAlignment = Alignment.Start,
                           modifier = Modifier
                               .fillMaxWidth()
                               .wrapContentHeight()) {
                        // Message Text with Adaptive Text Size and Accessibility Support
                        Text(text = message.text, style = MaterialTheme.typography.bodyLarge, // Scalable text size
                             modifier = Modifier
                                 .clip(RoundedCornerShape(12.dp))
                                 .background(MaterialTheme.colorScheme.surfaceVariant)
                                 .padding(8.dp)
                                 .semantics {
                                     contentDescription = message.text
                                 }, textAlign = TextAlign.Start)

                        // Message Date with Scalable Font and Accessibility
                        Text(text = "$messageDateLabel ${message.date.formatMessageDate(context)}",
                             style = MaterialTheme.typography.bodySmall, // Scalable text size
                             color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                             modifier = Modifier.semantics {
                                 contentDescription = "$messageDateLabel ${message.date.formatMessageDate(context)}"
                             })
                    }
                    Spacer(modifier = Modifier.width(60.dp))
                }

                // Product Details with Animation and Accessibility Support
                AnimatedVisibility(visible = expanded && product != null,
                                   enter = expandVertically(animationSpec = tween(durationMillis = 500)) + fadeIn(),
                                   exit = shrinkVertically(animationSpec = tween(durationMillis = 500)) + fadeOut()) {
                    Column(modifier = Modifier
                        .offset(x = (-12).dp)
                        .padding(end = 60.dp)) {
                        Spacer(modifier = Modifier.height(4.dp))
                        product?.let {
                            ProductItemView(product = product, onNavigateToProductView = onNavigateToProductView)
                        }
                    }
                }
            }
        }

        MessageType.IMAGE -> {
            Text(text = stringResource(id = R.string.image_message_received),
                 style = MaterialTheme.typography.bodyLarge,
                 modifier = Modifier.semantics { contentDescription = "Image message" })
        }

        MessageType.VIDEO -> {
            Text(text = stringResource(id = R.string.video_message_received),
                 style = MaterialTheme.typography.bodyLarge,
                 modifier = Modifier.semantics { contentDescription = "Video message" })
        }

        MessageType.AUDIO -> {
            Text(text = stringResource(id = R.string.audio_message_received),
                 style = MaterialTheme.typography.bodyLarge,
                 modifier = Modifier.semantics { contentDescription = "Audio message" })
        }

        MessageType.FILE -> {
            Text(text = stringResource(id = R.string.file_message_received),
                 style = MaterialTheme.typography.bodyLarge,
                 modifier = Modifier.semantics { contentDescription = "File message" })
        }
    }
}
