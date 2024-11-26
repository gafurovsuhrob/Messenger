package com.seros.messenger.domain.usecases.message

import com.seros.messenger.domain.repositories.MessageRepository

class DeleteMessageUseCase(private val messageRepository: MessageRepository) {
    suspend operator fun invoke(messageId: String) {
        messageRepository.deleteMessage(messageId)
    }
}