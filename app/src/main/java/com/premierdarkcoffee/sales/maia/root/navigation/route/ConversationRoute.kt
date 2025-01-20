package com.premierdarkcoffee.sales.maia.root.navigation.route

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.premierdarkcoffee.sales.maia.root.feature.chat.presentation.view.chat.component.ConversationView
import com.premierdarkcoffee.sales.maia.root.feature.chat.presentation.viewmodel.ChatViewModel
import com.premierdarkcoffee.sales.maia.root.navigation.ConversationRoute
import com.premierdarkcoffee.sales.maia.root.util.function.sharedViewModel

fun NavGraphBuilder.conversationRoute(navController: NavHostController, onNavigateToProductView: (String) -> Unit) {

    composable<ConversationRoute> { backStackEntry ->
        val viewModel = backStackEntry.sharedViewModel<ChatViewModel>(navController = navController)
        val args = backStackEntry.toRoute<ConversationRoute>()

        val messages by viewModel.messages.collectAsState()
        val groupedMessages = remember(messages) { messages.groupBy { it.clientId } }
        val clientId: String = args.clientId ?: ""
        val clientMessages = groupedMessages[clientId] ?: emptyList()

        ConversationView(messages = clientMessages,
                         onSendMessageToStoreButtonClicked = viewModel::sendMessage,
                         markMessageAsReadLaunchedEffect = viewModel::markMessageAsReadLaunchedEffect,
                         onNavigateToProductView)
    }
}
