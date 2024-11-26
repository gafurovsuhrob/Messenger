package com.seros.messenger.di

import android.content.Context
import androidx.room.Room
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.seros.messenger.data.auth.AuthManager
import com.seros.messenger.data.auth.AuthManagerImpl
import com.seros.messenger.data.database.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class AppModule() {

    @Provides
    fun provideGlideInstance(@ApplicationContext context: Context): RequestManager {
        return Glide.with(context)
    }

    @Provides
    fun provideAuthManager(@ApplicationContext context: Context): AuthManager {
        return AuthManagerImpl(context)
    }

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "messenger_database"
        ).build()
    }

    @Provides
    fun provideUserDao(appDatabase: AppDatabase) = appDatabase.userDao()

    @Provides
    fun provideChatDao(appDatabase: AppDatabase) = appDatabase.chatDao()

    @Provides
    fun provideMessageDao(appDatabase: AppDatabase) = appDatabase.messageDao()

    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth {
        return FirebaseAuth.getInstance()
    }

    @Provides
    @Singleton
    fun provideFirebaseFirestore(): FirebaseFirestore {
        return FirebaseFirestore.getInstance()
    }

    @Provides
    @Singleton
    fun provideFirebaseStorage(): FirebaseStorage {
        return FirebaseStorage.getInstance()
    }
}