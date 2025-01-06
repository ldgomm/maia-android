package com.premierdarkcoffee.sales.maia.root.feature.chat.presentation.view.chat.component

//
//  ConversationView.kt
//  Hermes
//
//  Created by José Ruiz on 13/7/24.
//

import android.content.ContentValues.TAG
import android.content.Context
import android.text.format.DateFormat
import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandIn
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.gson.Gson
import com.premierdarkcoffee.sales.maia.R
import com.premierdarkcoffee.sales.maia.root.feature.chat.data.local.entity.message.MessageEntity
import com.premierdarkcoffee.sales.maia.root.feature.chat.data.remote.dto.message.MessageDto
import com.premierdarkcoffee.sales.maia.root.feature.chat.domain.model.message.Message
import com.premierdarkcoffee.sales.maia.root.feature.chat.presentation.view.chat.titleStyle
import com.premierdarkcoffee.sales.maia.root.feature.product.data.remote.dto.ProductDto
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.TimeZone

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConversationView(
    messages: List<Message>,
    onSendMessageToStoreButtonClicked: (MessageDto) -> Unit,
    markMessageAsReadLaunchedEffect: (MessageEntity) -> Unit,
    onNavigateToProductView: (String) -> Unit
) {
    var inputText by remember { mutableStateOf("") }
    val scrollState = rememberScrollState()
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current

    val groupedMessages: List<Pair<Date, List<Message>>> = groupMessagesByDay(messages).toSortedMap().map { (key, value) ->
        key to value.sortedBy { it.date }
    }

    // Automatically scroll to the bottom when new messages are added
    LaunchedEffect(messages.size) {
        coroutineScope.launch {
            scrollState.animateScrollTo(scrollState.maxValue)
        }
    }

    Scaffold(topBar = {
        TopAppBar(title = {
            Text(
                text = messages.firstOrNull()?.clientId?.substring(startIndex = 0, endIndex = 6)?.lowercase() ?: "",
                style = titleStyle
            )
        })
    }) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            // Message list
            Column(
                modifier = Modifier
                    .verticalScroll(scrollState)
                    .weight(1f)
            ) {
                groupedMessages.forEach { (day, messages) ->
                    // Display day name in the center
                    Box(
                        modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = day.time.formatDayDate(context),
                            fontSize = 12.sp,
                            modifier = Modifier
                                .padding(4.dp)
                                .clip(RoundedCornerShape(4.dp))
                                .background(Color(0xFF008080).copy(alpha = 0.2f))
                                .padding(horizontal = 12.dp),
                            textAlign = TextAlign.Center
                        )
                    }
                    messages.forEach { message ->
                        if (message.fromClient) {
                            val product: ProductDto? = Gson().fromJson(message.product, ProductDto::class.java)
                            ClientMessageItemView(
                                message = message,
                                product = product?.toProduct(),
                                markMessageAsReadLaunchedEffect = markMessageAsReadLaunchedEffect,
                                onNavigateToProductView = onNavigateToProductView
                            )
                        } else {
                            StoreMessageItemView(message = message)
                        }
                    }
                }
            }

            // Input row
            Row(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Input field
                OutlinedTextField(
                    value = inputText,
                    onValueChange = { inputText = it },
                    modifier = Modifier
                        .weight(1f)
                        .background(Color.Transparent, shape = RoundedCornerShape(8.dp))
                        .padding(8.dp),
                    placeholder = { Text("Type a message...", color = Color.Gray) },
                    singleLine = true,
                    shape = RoundedCornerShape(8.dp),
                    keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Send),
                    keyboardActions = KeyboardActions(onSend = {
                        messages.firstOrNull()?.let { value ->
                            val message = MessageDto(
                                text = inputText, fromClient = false, clientId = value.clientId, storeId = value.storeId
                            )
                            Log.d(TAG, "ConversationView: $message")
                            onSendMessageToStoreButtonClicked(message)
                            inputText = ""
                        }
                    })
                )
                Spacer(modifier = Modifier.width(4.dp))

                // Send button visibility animation
                AnimatedVisibility(
                    visible = inputText.isNotEmpty(),
                    enter = expandIn(expandFrom = Alignment.Center) + fadeIn(),
                    exit = shrinkOut(shrinkTowards = Alignment.Center) + fadeOut()
                ) {
                    IconButton(onClick = {
                        messages.firstOrNull()?.let { value ->
                            val message = MessageDto(
                                text = inputText, fromClient = false, clientId = value.clientId, storeId = value.storeId
                            )
                            Log.d(TAG, "ConversationView: $message")
                            onSendMessageToStoreButtonClicked(message)
                            inputText = ""
                        }
                    }, modifier = Modifier.size(48.dp)) {
                        Icon(
                            ImageVector.vectorResource(R.drawable.send),
                            contentDescription = "Send button",
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }
            }
        }
    }
}

fun groupMessagesByDay(messages: List<Message>): Map<Date, List<Message>> {
    return messages.groupBy { it.day }
}

val Message.day: Date
    get() {
        // Convert the timestamp to milliseconds if needed
        val timeInterval = this.date

        // Create a Calendar instance using the default time zone
        val calendar = Calendar.getInstance(TimeZone.getDefault())

        // Set the calendar time based on the provided timestamp
        calendar.timeInMillis = timeInterval

        // Reset the time to the start of the day
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)

        // Return the modified date
        return calendar.time
    }


fun Long.formatDayDate(context: Context): String {
    val currentTime = System.currentTimeMillis()

    val calendar = Calendar.getInstance()
    calendar.timeInMillis = currentTime

    val currentYear = calendar.get(Calendar.YEAR)
    val currentDayOfYear = calendar.get(Calendar.DAY_OF_YEAR)

    calendar.timeInMillis = this
    val messageYear = calendar.get(Calendar.YEAR)
    val messageDayOfYear = calendar.get(Calendar.DAY_OF_YEAR)

    return when {
        currentDayOfYear == messageDayOfYear && currentYear == messageYear -> "Today"
        currentDayOfYear - messageDayOfYear == 1 && currentYear == messageYear -> "Yesterday"
        else -> {
            val is24Hour = DateFormat.is24HourFormat(context)
            val pattern = if (is24Hour) "MMM d, yyyy" else "MMM d, yyyy"
            val dateFormatter = SimpleDateFormat(
                pattern, Locale.getDefault()
            )
            dateFormatter.format(Date(this))
        }
    }
}