package com.seros.messenger.di

import com.seros.messenger.domain.repositories.AuthRepository
import com.seros.messenger.domain.repositories.ChatRepository
import com.seros.messenger.domain.repositories.MessageRepository
import com.seros.messenger.domain.repositories.UserRepository
import com.seros.messenger.domain.usecases.auth.GetCurrentUserUseCase
import com.seros.messenger.domain.usecases.auth.LoginUserUseCase
import com.seros.messenger.domain.usecases.auth.LogoutUserUseCase
import com.seros.messenger.domain.usecases.auth.RegisterUserUseCase
import com.seros.messenger.domain.usecases.chat.ClearChatMessagesUseCase
import com.seros.messenger.domain.usecases.chat.CreateChatUseCase
import com.seros.messenger.domain.usecases.chat.DeleteChatUseCase
import com.seros.messenger.domain.usecases.chat.GetChatsForUserUseCase
import com.seros.messenger.domain.usecases.message.DeleteMessageUseCase
import com.seros.messenger.domain.usecases.message.DeleteMessagesForChatUseCase
import com.seros.messenger.domain.usecases.message.GetMessagesForChatUseCase
import com.seros.messenger.domain.usecases.message.SendMessageUseCase
import com.seros.messenger.domain.usecases.user.GetUserByIdUseCase
import com.seros.messenger.domain.usecases.user.GetUserByPhoneNumberUseCase
import com.seros.messenger.domain.usecases.user.InsertUserUseCase
import com.seros.messenger.domain.usecases.user.UpdateUserUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
class UseCaseModule {

    @Provides
    fun provideLoginUserUseCase(authRepository: AuthRepository) = LoginUserUseCase(authRepository)

    @Provides
    fun provideRegisterUserUseCase(authRepository: AuthRepository) = RegisterUserUseCase(authRepository)

    @Provides
    fun provideGetCurrentUserUseCase(authRepository: AuthRepository) = GetCurrentUserUseCase(authRepository)

    @Provides
    fun provideLogoutUserUseCase(authRepository: AuthRepository) = LogoutUserUseCase(authRepository)

    @Provides
    fun provideInsertUserUseCase(userRepository: UserRepository) = InsertUserUseCase(userRepository)

    @Provides
    fun provideGetUserByIdUseCase(userRepository: UserRepository) = GetUserByIdUseCase(userRepository)

    @Provides
    fun provideGetUserByPhoneNumberUseCase(userRepository: UserRepository) = GetUserByPhoneNumberUseCase(userRepository)

    @Provides
    fun provideUpdateUserUseCase(userRepository: UserRepository) = UpdateUserUseCase(userRepository)

    @Provides
    fun provideGetChatsForUserUseCase(chatRepository: ChatRepository) = GetChatsForUserUseCase(chatRepository)

    @Provides
    fun provideCreateChatUseCase(chatRepository: ChatRepository) = CreateChatUseCase(chatRepository)

    @Provides
    fun provideClearChatMessagesUseCase(chatRepository: ChatRepository) = ClearChatMessagesUseCase(chatRepository)

    @Provides
    fun provideDeleteChatUseCase(chatRepository: ChatRepository) = DeleteChatUseCase(chatRepository)

    @Provides
    fun provideGetMessagesForChatUseCase(messageRepository: MessageRepository) = GetMessagesForChatUseCase(messageRepository)

    @Provides
    fun provideSendMessageUseCase(messageRepository: MessageRepository) = SendMessageUseCase(messageRepository)

    @Provides
    fun provideDeleteMessagesForChatUseCase(messageRepository: MessageRepository) = DeleteMessagesForChatUseCase(messageRepository)

    @Provides
    fun provideDeleteMessageUseCase(messageRepository: MessageRepository) = DeleteMessageUseCase(messageRepository)
}
