package com.seros.messenger.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.seros.messenger.data.database.dao.ChatDao
import com.seros.messenger.data.database.dao.MessageDao
import com.seros.messenger.data.database.dao.UserDao
import com.seros.messenger.data.repositories.AuthRepositoryImpl
import com.seros.messenger.data.repositories.ChatRepositoryImpl
import com.seros.messenger.data.repositories.MessageRepositoryImpl
import com.seros.messenger.data.repositories.UserRepositoryImpl
import com.seros.messenger.domain.repositories.AuthRepository
import com.seros.messenger.domain.repositories.ChatRepository
import com.seros.messenger.domain.repositories.MessageRepository
import com.seros.messenger.domain.repositories.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Provides
    fun provideUserRepository(userDao: UserDao): UserRepository = UserRepositoryImpl(userDao)

    @Provides
    fun provideChatRepository(chatDao: ChatDao): ChatRepository = ChatRepositoryImpl(chatDao)

    @Provides
    fun provideMessageRepository(messageDao: MessageDao): MessageRepository = MessageRepositoryImpl(messageDao)

    @Provides
    @Singleton
    fun provideAuthRepository(
        firebaseAuth: FirebaseAuth, firestore: FirebaseFirestore, storage: FirebaseStorage
    ): AuthRepository = AuthRepositoryImpl(firebaseAuth, firestore, storage)


}