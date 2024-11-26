package com.seros.messenger.domain.repositories

import android.net.Uri
import com.google.firebase.auth.FirebaseUser

interface AuthRepository {
    suspend fun registerUser(
        fullName: String,
        phoneNumber: String,
        password: String,
        profilePictureUri: Uri?
    ): Result<FirebaseUser?>
    suspend fun loginUser(phoneNumber: String, password: String): Result<FirebaseUser?>
    suspend fun logoutUser()
    suspend fun getCurrentUser(): FirebaseUser?
}
