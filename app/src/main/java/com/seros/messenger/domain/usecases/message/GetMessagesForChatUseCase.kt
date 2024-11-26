package com.seros.messenger.domain.usecases.message

import com.seros.messenger.data.database.entities.MessageEntity
import com.seros.messenger.domain.repositories.MessageRepository

class GetMessagesForChatUseCase(private val messageRepository: MessageRepository) {
    suspend operator fun invoke(chatId: String): List<MessageEntity> {
        return messageRepository.getMessagesForChat(chatId)
    }
}