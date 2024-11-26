package com.seros.messenger.data.database.entities

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "chats")
data class ChatEntity(
    @PrimaryKey val id: String,
    val lastMessage: String? = null,
    val lastMessageTime: String,
    val userId: String,
    val userName: String,
    val profilePictureUrl: String? = null
): Parcelable