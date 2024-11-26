package com.seros.messenger.domain.repositories

import com.seros.messenger.data.database.entities.ChatEntity
import com.seros.messenger.data.database.entities.MessageEntity

interface ChatRepository {
    suspend fun getChatsForUser(userId: String): List<ChatEntity>
    suspend fun getMessagesForChat(chatId: String): List<MessageEntity>
    suspend fun sendMessage(message: MessageEntity)
    suspend fun createChat(chat: ChatEntity)
    suspend fun clearChatMessages(chatId: String)
    suspend fun deleteChat(chatId: String)
}