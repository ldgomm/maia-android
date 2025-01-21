package com.premierdarkcoffee.sales.maia.root.feature.chat.data.local.repository

//
//  MessageRepository.kt
//  Maia
//
//  Created by José Ruiz on 14/7/24.
//

import android.content.ContentValues.TAG
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.premierdarkcoffee.sales.maia.root.feature.chat.data.local.database.MainDatabase
import com.premierdarkcoffee.sales.maia.root.feature.chat.data.local.entity.message.MessageEntity
import com.premierdarkcoffee.sales.maia.root.feature.chat.data.local.entity.message.MessageStatusEntity
import com.premierdarkcoffee.sales.maia.root.feature.chat.data.remote.dto.message.MessageDto
import com.premierdarkcoffee.sales.maia.root.feature.chat.domain.model.message.Message
import com.premierdarkcoffee.sales.maia.root.feature.chat.domain.repositoriable.MessageRepositoriable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MessageRepository @Inject constructor(private val database: MainDatabase) : MessageRepositoriable {
    private val db = FirebaseFirestore.getInstance().collection("messages")

    /**
     * Adds a new message to both the local database and the remote Firestore.
     *
     * @param message The [MessageDto] object representing the message to be added.
     */
    override suspend fun addMessage(message: MessageDto) {
        withContext(Dispatchers.IO) {
            try {
                db.add(message).await()
            } catch (e: Exception) {
                Log.d(TAG, "addMessage: Error adding message: ${e.message}")
            }
        }
    }

    /**
     * Fetches messages from the local database and remote Firestore, and updates the local database
     * with any new or updated messages from Firestore. The combined list of messages is then
     * provided to the callback function.
     *
     * @param callback A function that takes a list of [Message] objects and performs an action with them.
     */


    override suspend fun fetchMessages(callback: (List<Message>) -> Unit) {
        val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return
        db.whereEqualTo("storeId", userId).orderBy("date").addSnapshotListener { snapshot, error ->
            if (error != null) {
                return@addSnapshotListener
            }

            snapshot?.let { shot ->
                CoroutineScope(Dispatchers.IO).launch {
                    // Process the documents from Firestore and update the local database
                    val newMessages = shot.documents.mapNotNull { document ->
                        val messageDto = document.toObject(MessageDto::class.java)
                        messageDto?.toMessageEntity()?.toMessageDto()?.toMessage()
                    }
                    withContext(Dispatchers.Main) {
                        callback(newMessages)
                    }
                }
            }
        }
    }

//    override suspend fun fetchLocalMessages(callback: (List<Message>) -> Unit) {
//        // Fetch local messages from the local database
//        val localMessages = withContext(Dispatchers.IO) {
//            runCatching {
//                database.messageDao.getAllMessages().map { it.toMessageDto().toMessage() }
//            }.getOrElse {
//                emptyList()
//            }
//        }
//        callback(localMessages)
//    }

    override suspend fun markMessageAsRead(message: MessageEntity, onMessageUpdated: () -> Unit) {
        withContext(Dispatchers.IO) {
            try {
                val query = db.whereEqualTo("clientId", message.clientId).whereEqualTo("storeId", message.storeId)
                    .whereEqualTo("date", message.date).limit(1)
                val querySnapshot = query.get().await()
                if (!querySnapshot.isEmpty) {
                    val document = querySnapshot.documents[0]
                    document.reference.update("status", MessageStatusEntity.READ.name).await()
                    Log.d(TAG, "markMessageAsRead: Updated")
                } else {
                    Log.d(TAG, "markMessageAsRead: No matching document found")
                }
            } catch (e: Exception) {
                Log.d(TAG, "markMessageAsRead: Error marking message as read: ${e.message}")
            } finally {
                onMessageUpdated()
            }
        }
    }
}
