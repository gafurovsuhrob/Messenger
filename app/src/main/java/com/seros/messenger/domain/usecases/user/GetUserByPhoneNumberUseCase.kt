package com.seros.messenger.domain.usecases.user

import com.seros.messenger.data.database.entities.UserEntity
import com.seros.messenger.domain.repositories.UserRepository

class GetUserByPhoneNumberUseCase(private val userRepository: UserRepository) {
    suspend operator fun invoke(phoneNumber: String): UserEntity? {
        return userRepository.getUserByPhoneNumber(phoneNumber)
    }
}
