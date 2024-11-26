package com.seros.messenger.domain.usecases.auth

import android.net.Uri
import com.google.firebase.auth.FirebaseUser
import com.seros.messenger.domain.repositories.AuthRepository

class RegisterUserUseCase(private val authRepository: AuthRepository) {
    suspend operator fun invoke(fullName: String, phoneNumber: String, password: String, profilePictureUri: Uri?): Result<FirebaseUser?> {
        return authRepository.registerUser(fullName, phoneNumber, password, profilePictureUri)
    }
}