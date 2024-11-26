package com.seros.messenger.domain.usecases.auth

import com.seros.messenger.domain.repositories.AuthRepository

class LogoutUserUseCase(private val authRepository: AuthRepository) {
    suspend operator fun invoke() {
        authRepository.logoutUser()
    }
}