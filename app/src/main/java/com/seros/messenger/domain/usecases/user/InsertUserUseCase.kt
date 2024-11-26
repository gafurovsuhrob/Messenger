package com.seros.messenger.domain.usecases.user

import com.seros.messenger.data.database.entities.UserEntity
import com.seros.messenger.domain.repositories.UserRepository

class InsertUserUseCase(private val userRepository: UserRepository) {
    suspend operator fun invoke(user: UserEntity) {
        userRepository.insertUser(user)
    }
}