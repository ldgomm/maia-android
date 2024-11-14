package com.premierdarkcoffee.sales.maia.root.feature.chat.presentation.view.chat.component

//
//  ClientMessageItemView.kt
//  Hermes
//
//  Created by José Ruiz on 13/7/24.
//

import android.content.ContentValues.TAG
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.premierdarkcoffee.sales.maia.root.feature.chat.data.local.entity.message.MessageEntity
import com.premierdarkcoffee.sales.maia.root.feature.chat.domain.model.message.Message
import com.premierdarkcoffee.sales.maia.root.feature.chat.domain.model.message.MessageStatus
import com.premierdarkcoffee.sales.maia.root.feature.chat.domain.model.message.MessageType
import com.premierdarkcoffee.sales.maia.root.feature.product.domain.model.product.Product

@Composable
fun ClientMessageItemView(
    message: Message,
    product: Product? = null,
    markMessageAsReadLaunchedEffect: (MessageEntity) -> Unit,
    onNavigateToProductView: (String) -> Unit
) {
    // State to control the expanded/collapsed state of the product details
    val expanded by remember { mutableStateOf(true) }
    val context = LocalContext.current

    LaunchedEffect(message.status != MessageStatus.READ) {
        if (message.status != MessageStatus.READ) {
            Log.d(TAG, "ClientMessageItemView: Message status ${message.status}")
            markMessageAsReadLaunchedEffect(message.toMessageEntity())
        }
    }

    when (message.type) {
        MessageType.TEXT -> {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 12.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.Start
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                        .padding(start = 8.dp)
                        .wrapContentWidth(Alignment.Start)
                        .padding(vertical = 4.dp),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(2.dp),
                        horizontalAlignment = Alignment.Start,
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight()
                    ) {
                        Text(
                            text = message.text,
                            fontSize = 16.sp,
                            modifier = Modifier
                                .clip(RoundedCornerShape(12.dp))
                                .background(Color.Gray.copy(alpha = 0.2f))
                                .padding(8.dp),
                            textAlign = TextAlign.Start
                        )

                        Text(
                            text = message.date.formatMessageDate(context),
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                        )
                    }
                    Spacer(modifier = Modifier.width(60.dp))
                }
                AnimatedVisibility(
                    visible = expanded && product != null,
                    enter = expandVertically(animationSpec = tween(durationMillis = 500)) + fadeIn(),
                    exit = shrinkVertically(animationSpec = tween(durationMillis = 500)) + fadeOut()
                ) {
                    Column(
                        modifier = Modifier
                            .offset(x = (-12).dp)
                            .padding(end = 60.dp)
                    ) {  // Offset 10 to the left
                        Spacer(modifier = Modifier.height(4.dp))
                        product?.let {
                            ProductItemView(product, onNavigateToProductView)
                        }
                    }
                }

            }
        }

        MessageType.IMAGE -> TODO()
        MessageType.VIDEO -> TODO()
        MessageType.AUDIO -> TODO()
        MessageType.FILE -> TODO()
    }
}
