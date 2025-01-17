package com.premierdarkcoffee.sales.maia.root.feature.chat.presentation.view.chat.component

//
//  ConversationItemView.kt
//  Hermes
//
//  Created by José Ruiz on 13/7/24.
//

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.premierdarkcoffee.sales.maia.R
import com.premierdarkcoffee.sales.maia.R.drawable.lock_person
import com.premierdarkcoffee.sales.maia.root.feature.chat.domain.model.message.Message
import com.premierdarkcoffee.sales.maia.root.util.extension.formatShortDate

@Composable
fun ConversationItemView(
    message: Message,
    sentOrDeliveredCount: Int,
    onConversationItemViewClicked: () -> Unit
) {
    val messageDateLabel = stringResource(id = R.string.message_date)
    val storeIconDescription = stringResource(id = R.string.store_icon_description)
    val newMessagesLabel = stringResource(id = R.string.new_messages_label)

    Row(
        modifier = Modifier
            .padding(vertical = 8.dp)
            .fillMaxWidth()
            .clickable { onConversationItemViewClicked() }
            .semantics { contentDescription = "${message.clientId} $newMessagesLabel $sentOrDeliveredCount" }
    ) {
        // Store icon with accessibility
        Image(
            painter = painterResource(lock_person),
            contentDescription = storeIconDescription,
            modifier = Modifier
                .size(54.dp)
                .clip(CircleShape)
                .padding(12.dp),
            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.tertiary)
        )
        Spacer(modifier = Modifier.width(10.dp))

        // Message content
        Column(modifier = Modifier.weight(1f)) {
            // Client ID with adaptive text size using theme typography
            Text(
                text = message.clientId.substring(startIndex = 0, endIndex = 6).lowercase(),
                style = MaterialTheme.typography.titleMedium, // Scalable text size
                fontWeight = FontWeight.Bold,
                modifier = Modifier.semantics { contentDescription = "Client ID: ${message.clientId}" }
            )

            // Message text with adaptive text size
            Text(
                text = message.text,
                style = MaterialTheme.typography.bodyMedium, // Scalable text size
                color = Color.Gray,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.semantics {
                    contentDescription = "${message.text} $messageDateLabel ${message.date.formatShortDate()}"
                }
            )
        }
        Spacer(modifier = Modifier.width(10.dp))

        // Date and Message Count Badge with Accessibility
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Date with dynamic font size support
            Text(
                text = message.date.formatShortDate(),
                style = MaterialTheme.typography.bodySmall, // Scalable text size
                color = Color.Gray,
                modifier = Modifier
                    .padding(end = 10.dp)
                    .semantics {
                        contentDescription = "$messageDateLabel ${message.date.formatShortDate()}"
                    }
            )

            // Message count badge with proper scaling
            if (sentOrDeliveredCount > 0) {
                Box(
                    modifier = Modifier
                        .size(24.dp)
                        .background(Color.DarkGray.copy(0.7f), shape = CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "$sentOrDeliveredCount",
                        style = MaterialTheme.typography.labelSmall.copy(color = Color.White), // Scalable
                        modifier = Modifier.semantics {
                            contentDescription = "$sentOrDeliveredCount $newMessagesLabel"
                        }
                    )
                }
            }
        }
    }
}
