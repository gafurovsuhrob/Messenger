package com.seros.messenger.domain.repositories

import com.seros.messenger.data.database.entities.MessageEntity


interface MessageRepository {
    suspend fun getMessagesForChat(chatId: String): List<MessageEntity>
    suspend fun sendMessage(message: MessageEntity)
    suspend fun deleteMessagesForChat(chatId: String)
    suspend fun deleteMessage(messageId: String)
}