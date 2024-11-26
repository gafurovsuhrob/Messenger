package com.seros.messenger.data.database


import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.seros.messenger.data.database.entities.UserEntity
import com.seros.messenger.data.database.dao.ChatDao
import com.seros.messenger.data.database.dao.MessageDao
import com.seros.messenger.data.database.dao.UserDao
import com.seros.messenger.data.database.entities.ChatEntity
import com.seros.messenger.data.database.entities.MessageEntity

@Database(entities = [UserEntity::class, ChatEntity::class, MessageEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
    abstract fun chatDao(): ChatDao
    abstract fun messageDao(): MessageDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "messenger_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
