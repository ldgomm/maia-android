package com.premierdarkcoffee.sales.maia.root.feature.chat.presentation.view.chat.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun NewChatItem(
    imageRes: Int,
    title: String,
    subtitle: String,
    date: String,
    hasVerification: Boolean = false,
    onNewChatButtonClicked: () -> Unit
) {
    Row(modifier = Modifier
        .fillMaxWidth()
        .background(Color.LightGray.copy(0.05f), shape = RoundedCornerShape(8.dp))
        .padding(8.dp)
        .clickable { onNewChatButtonClicked() }, verticalAlignment = Alignment.CenterVertically
    ) {
        // Image and verification icon
        Box {
            Image(
                painter = painterResource(id = imageRes),
                contentDescription = null,
                modifier = Modifier
                    .size(50.dp)
                    .background(Color.Blue.copy(0.1f), shape = CircleShape)
                    .padding(8.dp)
            )
            if (hasVerification) {
                Icon(
                    imageVector = Icons.Default.CheckCircle,
                    contentDescription = null,
                    tint = Color.Blue,
                    modifier = Modifier
                        .size(16.dp)
                        .align(Alignment.TopEnd)
                        .offset(x = 5.dp, y = (-5).dp)
                )
            }
        }
        Spacer(modifier = Modifier.width(8.dp))

        // Title and subtitle
        Column(modifier = Modifier.weight(1f)) {
            Text(title, fontWeight = FontWeight.Bold, fontSize = 16.sp)
            Text(subtitle, fontSize = 14.sp, color = Color.Gray, maxLines = 1, overflow = TextOverflow.Ellipsis)
        }

        // Date text
        Text(date, fontSize = 12.sp, color = Color.Gray)
    }
}
