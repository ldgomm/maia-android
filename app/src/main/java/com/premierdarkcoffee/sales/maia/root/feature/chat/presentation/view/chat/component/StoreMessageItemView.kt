package com.premierdarkcoffee.sales.maia.root.feature.chat.presentation.view.chat.component

//
//  StoreMessageItemView.kt
//  Hermes
//
//  Created by José Ruiz on 13/7/24.
//

import android.content.Context
import android.text.format.DateFormat
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.premierdarkcoffee.sales.maia.root.feature.chat.domain.model.message.Message
import com.premierdarkcoffee.sales.maia.root.feature.chat.domain.model.message.MessageType.AUDIO
import com.premierdarkcoffee.sales.maia.root.feature.chat.domain.model.message.MessageType.FILE
import com.premierdarkcoffee.sales.maia.root.feature.chat.domain.model.message.MessageType.IMAGE
import com.premierdarkcoffee.sales.maia.root.feature.chat.domain.model.message.MessageType.TEXT
import com.premierdarkcoffee.sales.maia.root.feature.chat.domain.model.message.MessageType.VIDEO
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

@Composable
fun StoreMessageItemView(message: Message) {

    val context = LocalContext.current
    when (message.type) {
        TEXT -> {
            Spacer(Modifier.padding(horizontal = 60.dp))
            Row(
                modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End, verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(end = 12.dp)
                        .padding(top = 8.dp)
                        .fillMaxWidth(0.8f)
                        .wrapContentWidth(Alignment.End),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.End
                ) {
                    Text(
                        text = message.text, fontSize = 16.sp, modifier = Modifier
                            .clip(RoundedCornerShape(12.dp))
                            .background(
                                MaterialTheme.colorScheme.primary.copy(
                                    if (isSystemInDarkTheme()) 0.2f else 0.9f
                                )
                            )
                            .padding(8.dp), color = Color.White, textAlign = TextAlign.Start
                    )

                    Row(
                        verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.End, modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = message.date.formatMessageDate(context), fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                        )
                    }
                }
            }
        }

        IMAGE -> {
            Text("Image")
        }

        VIDEO -> {
            Text("Video")
        }

        AUDIO -> {
            Text("Audio")
        }

        FILE -> {
            Text("File")
        }
    }
}

fun Long.formatMessageDate(context: Context): String {
    val currentTime = System.currentTimeMillis()
    val diff = currentTime - this

    val oneMinute = 60 * 1000
    val oneHour = 60 * oneMinute
    val oneDay = 24 * oneHour

    val calendar = Calendar.getInstance()
    calendar.timeInMillis = currentTime

    val currentDayOfYear = calendar.get(Calendar.DAY_OF_YEAR)

    calendar.timeInMillis = this
    val messageDayOfYear = calendar.get(Calendar.DAY_OF_YEAR)

    return when {
        diff < oneMinute -> "Just now"
        diff < oneHour -> "${diff / oneMinute} min ago"
        diff < oneDay && currentDayOfYear == messageDayOfYear -> {
            val is24Hour = DateFormat.is24HourFormat(context)
            val pattern = if (is24Hour) "HH:mm" else "hh:mm a"
            val dateFormatter = SimpleDateFormat(
                pattern, Locale.getDefault()
            )
            dateFormatter.format(Date(this))
        }

        diff < 2 * oneDay && currentDayOfYear - messageDayOfYear == 1 -> {
            val is24Hour = DateFormat.is24HourFormat(context)
            val pattern = if (is24Hour) "HH:mm" else "hh:mm a"
            val dateFormatter = SimpleDateFormat(
                pattern, Locale.getDefault()
            )
            dateFormatter.format(Date(this))
        }

        else -> {
            val is24Hour = DateFormat.is24HourFormat(context)
            val pattern = if (is24Hour) "MMM d, yyyy, HH:mm" else "MMM d, yyyy, hh:mm a"
            val dateFormatter = SimpleDateFormat(
                pattern, Locale.getDefault()
            )
            dateFormatter.format(Date(this))
        }
    }
}
