package com.premierdarkcoffee.sales.maia.root.feature.chat.presentation.view.chat.component

//
//  StoreMessageItemView.kt
//  Hermes
//
//  Created by José Ruiz on 13/7/24.
//

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.premierdarkcoffee.sales.maia.root.feature.chat.domain.model.message.Message

@Composable
fun StoreMessageItemView(message: Message) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(end = 12.dp),
        horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = message.text,
            fontSize = 16.sp,
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .wrapContentWidth(Alignment.End)
                .padding(top = 4.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(
                    MaterialTheme.colorScheme.primary.copy(
                        if (isSystemInDarkTheme()) 0.2f else 0.99f
                    )
                )                .padding(8.dp),
            color = Color.White
        )
    }
}
