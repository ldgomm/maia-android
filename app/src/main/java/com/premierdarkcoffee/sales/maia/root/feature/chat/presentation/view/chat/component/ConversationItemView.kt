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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.premierdarkcoffee.sales.maia.R.drawable.lock_person
import com.premierdarkcoffee.sales.maia.root.feature.chat.domain.model.message.Message
import com.premierdarkcoffee.sales.maia.root.util.extension.formatShortDate

@Composable
fun ConversationItemView(
    message: Message,
    sentOrDeliveredCount: Int,
    onConversationItemViewClicked: () -> Unit
) {
    Row(modifier = Modifier
        .padding(vertical = 8.dp)
        .fillMaxWidth()
        .clickable { onConversationItemViewClicked() }) {
        // Store icon
        Image(
            painter = painterResource(lock_person),
            contentDescription = "Storefront",
            modifier = Modifier
                .size(54.dp)
                .clip(CircleShape)
                .padding(12.dp),
            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.tertiary)
        )
        Spacer(modifier = Modifier.width(10.dp))

        // Message content
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = message.clientId.substring(startIndex = 0, endIndex = 6).lowercase(),
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
            Text(text = message.text, fontSize = 12.sp, color = Color.Gray, maxLines = 2, overflow = TextOverflow.Ellipsis)
        }
        Spacer(modifier = Modifier.width(10.dp))
        Column(verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
            // Date text
            Text(
                text = message.date.formatShortDate(),
                fontSize = 14.sp,
                color = Color.Gray,
                modifier = Modifier.padding(end = 10.dp)
            )

            // Message count badge
            if (sentOrDeliveredCount > 0) {
                Box(
                    modifier = Modifier
                        .size(24.dp)
                        .background(Color.DarkGray.copy(0.7f), shape = CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "$sentOrDeliveredCount",
                        fontSize = 10.sp, // Slightly larger text size
                        color = Color.White
                    )
                }
            }
        }
    }
}
