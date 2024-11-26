package com.seros.messenger.data.repositories

import android.net.Uri
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.seros.messenger.data.models.User
import com.seros.messenger.domain.repositories.AuthRepository
import com.seros.messenger.utils.PasswordHasher
import kotlinx.coroutines.tasks.await
import java.util.Base64
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firestore: FirebaseFirestore,
    private val storage: FirebaseStorage
) : AuthRepository {

    override suspend fun registerUser(
        fullName: String,
        phoneNumber: String,
        password: String,
        profilePictureUri: Uri?
    ): Result<FirebaseUser?> {
        return try {
            val snapshot = firestore.collection("users")
                .whereEqualTo("phoneNumber", phoneNumber)
                .get()
                .await()

            if (!snapshot.isEmpty) {
                return Result.failure(Exception("Пользователь с таким номером телефона уже существует"))
            }

            val userId = firestore.collection("users").document().id

            val salt = PasswordHasher.generateSalt()
            val hashedPassword = PasswordHasher.hashPassword(password, salt)

            var profilePictureUrl: String? = null

            if (profilePictureUri != null) {
                val storageReference = storage.reference.child("profile_pictures/$userId.jpg")
                val uploadTask = storageReference.putFile(profilePictureUri).await()
                profilePictureUrl = storageReference.downloadUrl.await().toString()
            }

            val user = User(
                id = userId,
                fullName = fullName,
                phoneNumber = phoneNumber,
                profilePictureUrl = profilePictureUrl,
                password = hashedPassword,
                salt = Base64.getEncoder().encodeToString(salt),
                isOnline = false
            )

            firestore.collection("users").document(userId).set(user).await()
            Result.success(firebaseAuth.currentUser)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun loginUser(phoneNumber: String, password: String): Result<FirebaseUser?> {
        return try {
            val snapshot = firestore.collection("users")
                .whereEqualTo("phoneNumber", phoneNumber)
                .get()
                .await()

            if (snapshot.isEmpty) {
                return Result.failure(Exception("Пользователь не найден"))
            }

            val user = snapshot.documents.first().toObject(User::class.java)
                ?: return Result.failure(Exception("Ошибка загрузки данных пользователя"))

            val salt = Base64.getDecoder().decode(user.salt)
            if (!PasswordHasher.verifyPassword(password, user.password, salt)) {
                return Result.failure(Exception("Неверный пароль"))
            }

            firebaseAuth.signInAnonymously().await()

            Result.success(firebaseAuth.currentUser)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun logoutUser() {
        firebaseAuth.signOut()
    }

    override suspend fun getCurrentUser(): FirebaseUser? {
        return firebaseAuth.currentUser
    }
}

