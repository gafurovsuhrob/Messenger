package com.seros.messenger.data.repositories

import com.seros.messenger.data.database.dao.UserDao
import com.seros.messenger.data.database.entities.UserEntity
import com.seros.messenger.domain.repositories.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userDao: UserDao
) : UserRepository {

    override suspend fun insertUser(user: UserEntity) {
        userDao.insertUser(user)
    }

    override suspend fun getUserById(userId: String): UserEntity {
        return userDao.getUserById(userId)
    }

    override suspend fun getUserByPhoneNumber(phoneNumber: String): UserEntity {
        return userDao.getUserByPhoneNumber(phoneNumber)
    }

    override suspend fun updateUser(user: UserEntity) {
        userDao.updateUser(user)
    }
}
