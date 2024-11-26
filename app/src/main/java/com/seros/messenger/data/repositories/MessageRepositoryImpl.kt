package com.seros.messenger.data.repositories


import com.seros.messenger.data.database.dao.MessageDao
import com.seros.messenger.data.database.entities.MessageEntity
import com.seros.messenger.domain.repositories.MessageRepository

class MessageRepositoryImpl(
    private val messageDao: MessageDao
) : MessageRepository {

    override suspend fun getMessagesForChat(chatId: String): List<MessageEntity> {
        return messageDao.getMessagesForChat(chatId)
    }

    override suspend fun sendMessage(message: MessageEntity) {
        messageDao.insertMessage(message)
    }

    override suspend fun deleteMessagesForChat(chatId: String) {
        messageDao.deleteMessagesForChat(chatId)
    }

    override suspend fun deleteMessage(messageId: String) {
        messageDao.deleteMessage(messageId)
    }
}