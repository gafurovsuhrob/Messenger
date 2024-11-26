package com.seros.messenger.data.firebase.auth

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.tasks.await


class FirebaseAuthManager(
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
) {

    suspend fun registerUser(fullName: String, phoneNumber: String, password: String): FirebaseUser? {
        val result = auth.createUserWithEmailAndPassword(phoneNumber, password).await()
        return result.user
    }

    suspend fun loginUser(email: String, password: String): FirebaseUser? {
        val result = auth.signInWithEmailAndPassword(email, password).await()
        return result.user
    }

    fun getCurrentUser(): FirebaseUser? = auth.currentUser

    fun logout() {
        auth.signOut()
    }
}
