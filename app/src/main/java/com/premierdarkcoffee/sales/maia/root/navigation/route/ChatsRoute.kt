package com.premierdarkcoffee.sales.maia.root.navigation.route

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.premierdarkcoffee.sales.maia.root.feature.chat.presentation.view.chat.ChatsView
import com.premierdarkcoffee.sales.maia.root.feature.chat.presentation.viewmodel.ChatViewModel
import com.premierdarkcoffee.sales.maia.root.navigation.ChatsRoute
import com.premierdarkcoffee.sales.maia.root.util.function.sharedViewModel

fun NavGraphBuilder.chatsRoute(navController: NavHostController, onConversationItemViewClicked: (String) -> Unit) {

    composable<ChatsRoute> { backStackEntry ->
        val viewModel = backStackEntry.sharedViewModel<ChatViewModel>(navController = navController)
        val messages by viewModel.messages.collectAsState()

        ChatsView(messages = messages, onConversationItemViewClicked = onConversationItemViewClicked)
    }
}
