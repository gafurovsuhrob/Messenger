package com.seros.messenger.data.firebase.storage


import android.net.Uri
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.tasks.await

class FirebaseStorageManager(
    private val storage: FirebaseStorage = FirebaseStorage.getInstance()
) {

    suspend fun uploadProfilePicture(userId: String, fileUri: Uri): String {
        val reference = storage.reference.child("profile_pictures/$userId.jpg")
        reference.putFile(fileUri).await()
        return reference.downloadUrl.await().toString()
    }
}
