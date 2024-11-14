package com.premierdarkcoffee.sales.maia.root.feature.chat.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.premierdarkcoffee.sales.maia.root.feature.chat.data.local.entity.message.MessageEntity
import com.premierdarkcoffee.sales.maia.root.feature.chat.data.remote.dto.message.MessageDto
import com.premierdarkcoffee.sales.maia.root.feature.chat.domain.model.message.Message
import com.premierdarkcoffee.sales.maia.root.feature.chat.domain.usecase.FetchMessageUseCase
import com.premierdarkcoffee.sales.maia.root.feature.chat.domain.usecase.MarkMessageAsReadUseCase
import com.premierdarkcoffee.sales.maia.root.feature.chat.domain.usecase.SendMessageToClientUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val sendMessageToClientUseCase: SendMessageToClientUseCase,
    private val markMessageAsReadUseCase: MarkMessageAsReadUseCase,
    private val fetchRemoteMessageUseCase: FetchMessageUseCase,
//    private val fetchLocalMessagesUseCase: FetchLocalMessagesUseCase
) : ViewModel() {

    // State flows to manage chat messages, messages, typing status, and cart products
    private val _messages = MutableStateFlow<List<Message>>(emptyList())
    val messages: StateFlow<List<Message>> get() = _messages

    init {
        getRemoteMessages()
    }

    /**
     * Sends a message to the store.
     *
     * @param message The [MessageDto] object representing the message to be sent.
     */
    fun sendMessage(message: MessageDto) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                sendMessageToClientUseCase.invoke(message)
            }
        }
    }

    fun markMessageAsReadLaunchedEffect(message: MessageEntity) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                markMessageAsReadUseCase.invoke(message) {
//                    getLocalMessages()
                }
            }
        }
    }

    /**
     * Fetches messages and updates the messages state flow.
     */
    private fun getRemoteMessages() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                fetchRemoteMessageUseCase.invoke { messages ->
                    _messages.value = messages
                }
            }
        }
    }
}
