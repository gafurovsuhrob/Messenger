package com.seros.messenger.domain.usecases.chat

import com.seros.messenger.domain.repositories.ChatRepository

class ClearChatMessagesUseCase(private val chatRepository: ChatRepository) {
    suspend operator fun invoke(chatId: String) {
        chatRepository.clearChatMessages(chatId)
    }
}