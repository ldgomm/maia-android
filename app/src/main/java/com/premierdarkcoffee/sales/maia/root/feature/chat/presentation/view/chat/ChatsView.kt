package com.premierdarkcoffee.sales.maia.root.feature.chat.presentation.view.chat

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.premierdarkcoffee.sales.maia.root.feature.chat.domain.model.message.Message
import com.premierdarkcoffee.sales.maia.root.feature.chat.domain.model.message.MessageStatus
import com.premierdarkcoffee.sales.maia.root.feature.chat.presentation.view.chat.component.ConversationItemView

/**
 * A composable function that displays a list of chat messages grouped by store.
 *
 * This function uses a scaffold with a top app bar and a lazy column to display pinned messages,
 * a new chat item, and grouped chat messages from different stores. It provides options to create
 * a new chat and to view conversations with specific stores.
 *
 * @param messages The list of chat messages to be displayed.
 * @param onConversationItemViewClicked A callback function that is invoked when a conversation item is clicked.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatsView(
    messages: List<Message>,
    onConversationItemViewClicked: (String) -> Unit
) {
    // Group messages by storeId
    val sortedGroupedMessages = remember(messages) {
        messages.groupBy { it.clientId }.mapValues { entry -> entry.value.sortedBy { it.date } }.toList()
            .sortedByDescending { it.second.lastOrNull()?.date }
    }
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

    Scaffold(modifier = Modifier
        .background(MaterialTheme.colorScheme.surface)
        .statusBarsPadding()
        .navigationBarsPadding()
        .nestedScroll(scrollBehavior.nestedScrollConnection), topBar = {
        TopAppBar(title = { Text("Chats", style = titleStyle) })
    }) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .padding(
                    top = paddingValues.calculateTopPadding(),
                    start = paddingValues.calculateLeftPadding(LayoutDirection.Ltr),
                    end = paddingValues.calculateRightPadding(LayoutDirection.Ltr),
                    bottom = paddingValues.calculateBottomPadding()
                )
                .padding(8.dp)
                .fillMaxSize()
        ) {
            // Display each store's last message
            sortedGroupedMessages.forEach { (clientId, clientMessages) ->
                item {
                    val lastMessage = clientMessages.lastOrNull()
                    lastMessage?.let { message ->
                        val sentOrDeliveredCount = clientMessages.count {
                            (it.status == MessageStatus.SENT || it.status == MessageStatus.DELIVERED) && it.fromClient
                        }
                        ConversationItemView(message = message,
                                             sentOrDeliveredCount = sentOrDeliveredCount,
                                             onConversationItemViewClicked = {
                                                 onConversationItemViewClicked(clientId)
                                             })
                    }
                }
            }
        }
    }
}

val titleStyle: TextStyle = TextStyle(fontSize = 29.sp, fontWeight = FontWeight.Bold, fontStyle = FontStyle.Normal)
