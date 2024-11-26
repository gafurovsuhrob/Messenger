package com.seros.messenger.data.repositories


import com.seros.messenger.data.database.dao.ChatDao
import com.seros.messenger.data.database.entities.ChatEntity
import com.seros.messenger.data.database.entities.MessageEntity
import com.seros.messenger.domain.repositories.ChatRepository

class ChatRepositoryImpl(
    private val chatDao: ChatDao
) : ChatRepository {

    override suspend fun getChatsForUser(userId: String): List<ChatEntity> {
        return chatDao.getChatsForUser(userId)
    }

    override suspend fun getMessagesForChat(chatId: String): List<MessageEntity> {
        return chatDao.getMessagesForChat(chatId)
    }

    override suspend fun sendMessage(message: MessageEntity) {
        chatDao.insertMessage(message)
    }

    override suspend fun createChat(chat: ChatEntity) {
        chatDao.insertChat(chat)
    }

    override suspend fun clearChatMessages(chatId: String) {
        chatDao.clearChatMessages(chatId)
    }

    override suspend fun deleteChat(chatId: String) {
        chatDao.deleteChat(chatId)
    }
}
