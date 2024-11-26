package com.seros.messenger.data.database.dao


import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.seros.messenger.data.database.entities.UserEntity

@Dao
interface UserDao {

    @Insert
    suspend fun insertUser(user: UserEntity)

    @Update
    suspend fun updateUser(user: UserEntity)

    @Query("SELECT * FROM users WHERE id = :userId")
    suspend fun getUserById(userId: String): UserEntity  // Получить пользователя по ID

    @Query("SELECT * FROM users WHERE phoneNumber = :phoneNumber")
    suspend fun getUserByPhoneNumber(phoneNumber: String): UserEntity  // Получить пользователя по номеру телефона
}

//interface UserDao {
//
//    // Получаем пользователя по ID
//    @Query("SELECT * FROM users WHERE userId = :userId")
//    suspend fun getUserById(userId: Long): UserEntity?
//
//    // Получаем всех пользователей (для списка контактов)
//    @Query("SELECT * FROM users")
//    suspend fun getAllUsers(): List<UserEntity>
//
//    // Добавляем нового пользователя
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    suspend fun insertUser(user: UserEntity)
//
//    // Удаление пользователя по ID
//    @Query("DELETE FROM users WHERE userId = :userId")
//    suspend fun deleteUser(userId: Long)
//}