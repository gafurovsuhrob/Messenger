package com.seros.messenger.domain.repositories

import com.seros.messenger.data.database.entities.UserEntity


interface UserRepository {
    suspend fun insertUser(user: UserEntity)
    suspend fun getUserById(userId: String): UserEntity?
    suspend fun getUserByPhoneNumber(phoneNumber: String): UserEntity?
    suspend fun updateUser(user: UserEntity)
}
