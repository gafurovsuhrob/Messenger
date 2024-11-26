package com.seros.messenger.data.database.dao


import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.OnConflictStrategy
import androidx.room.Transaction
import com.seros.messenger.data.database.entities.ChatEntity
import com.seros.messenger.data.database.entities.MessageEntity

@Dao
interface ChatDao {

    @Transaction
    @Query("SELECT * FROM chats WHERE userId = :userId")
    suspend fun getChatsForUser(userId: String): List<ChatEntity>

    @Transaction
    @Query("SELECT * FROM messages WHERE chatId = :chatId ORDER BY timestamp ASC")
    suspend fun getMessagesForChat(chatId: String): List<MessageEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertChat(chat: ChatEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMessage(message: MessageEntity)

    @Query("DELETE FROM chats WHERE id = :chatId")
    suspend fun deleteChat(chatId: String)

    @Query("DELETE FROM messages WHERE chatId = :chatId")
    suspend fun clearChatMessages(chatId: String)
}
