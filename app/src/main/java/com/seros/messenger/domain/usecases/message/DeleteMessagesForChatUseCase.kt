package com.seros.messenger.domain.usecases.message

import com.seros.messenger.data.database.entities.MessageEntity
import com.seros.messenger.domain.repositories.MessageRepository

class DeleteMessagesForChatUseCase(private val messageRepository: MessageRepository) {
    suspend operator fun invoke(chatId: String) {
        messageRepository.deleteMessagesForChat(chatId)
    }
}