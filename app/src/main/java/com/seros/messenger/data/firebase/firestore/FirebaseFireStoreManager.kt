package com.seros.messenger.data.firebase.firestore

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import com.seros.messenger.data.database.entities.ChatEntity
import com.seros.messenger.data.database.entities.MessageEntity
import com.seros.messenger.data.database.entities.UserEntity
import kotlinx.coroutines.tasks.await

class FirebaseFireStoreManager(
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
) {

    suspend fun getUserById(userId: String): UserEntity? {
        val snapshot = firestore.collection("users").document(userId).get().await()
        return snapshot.toObject(UserEntity::class.java)
    }

    suspend fun saveUser(user: UserEntity) {
        firestore.collection("users").document(user.id.toString()).set(user).await()
    }

    suspend fun getChatsForUser(userId: String): List<ChatEntity> {
        val snapshot = firestore.collection("chats")
            .whereEqualTo("participants.$userId", userId)
            .get().await()
        return snapshot.toObjects(ChatEntity::class.java)
    }

    suspend fun sendMessage(message: MessageEntity) {
        firestore.collection("messages").document(message.id.toString()).set(message).await()
    }
}

