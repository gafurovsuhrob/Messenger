package com.seros.messenger.domain.usecases.auth

import com.google.firebase.auth.FirebaseUser
import com.seros.messenger.domain.repositories.AuthRepository

class LoginUserUseCase(private val authRepository: AuthRepository) {
    suspend operator fun invoke(phoneNumber: String, password: String): Result<FirebaseUser?> {
        return authRepository.loginUser(phoneNumber, password)
    }
}