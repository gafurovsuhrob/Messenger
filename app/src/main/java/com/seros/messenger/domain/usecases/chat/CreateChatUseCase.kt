package com.seros.messenger.domain.usecases.chat

import com.seros.messenger.data.database.entities.ChatEntity
import com.seros.messenger.domain.repositories.ChatRepository

class CreateChatUseCase(private val chatRepository: ChatRepository) {
    suspend operator fun invoke(chat: ChatEntity) {
        chatRepository.createChat(chat)
    }
}
