package com.seros.messenger.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "messages")
data class MessageEntity(
    @PrimaryKey val id: String,
    val chatId: String,
    val senderId: String,
    val messageContent: String? = null,
    val messageType: MessageType,
    val timestamp: String,
    val status: MessageStatus = MessageStatus.PENDING
)

enum class MessageType {
    TEXT, IMAGE, VIDEO, FILE, LOCATION
}

enum class MessageStatus {
    PENDING, SENT, DELIVERED, READ
}
