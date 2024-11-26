package com.seros.messenger.domain.usecases.chat

import com.seros.messenger.data.database.entities.ChatEntity
import com.seros.messenger.domain.repositories.ChatRepository

class GetChatsForUserUseCase(private val chatRepository: ChatRepository) {
    suspend operator fun invoke(userId: String): List<ChatEntity> {
        return chatRepository.getChatsForUser(userId)
    }
}