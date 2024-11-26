package com.seros.messenger.domain.usecases.user

import com.seros.messenger.data.database.entities.UserEntity
import com.seros.messenger.domain.repositories.UserRepository

class GetUserByIdUseCase(private val userRepository: UserRepository) {
    suspend operator fun invoke(userId: String): UserEntity? {
        return userRepository.getUserById(userId)
    }
}