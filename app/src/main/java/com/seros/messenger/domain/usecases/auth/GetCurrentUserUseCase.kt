package com.seros.messenger.domain.usecases.auth

import com.google.firebase.auth.FirebaseUser
import com.seros.messenger.domain.repositories.AuthRepository

class GetCurrentUserUseCase(private val authRepository: AuthRepository) {
    suspend operator fun invoke(): FirebaseUser? {
        return authRepository.getCurrentUser()
    }
}