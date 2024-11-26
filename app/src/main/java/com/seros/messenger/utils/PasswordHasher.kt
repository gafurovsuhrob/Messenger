package com.seros.messenger.utils

import android.annotation.SuppressLint
import java.security.SecureRandom
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.PBEKeySpec
import java.util.Base64

object PasswordHasher {

    private const val SALT_LENGTH = 16
    private const val HASH_LENGTH = 32
    private const val ITERATIONS = 10000
    private const val ALGORITHM = "PBKDF2WithHmacSHA256"

    // Хеширование пароля
    fun hashPassword(password: String, salt: ByteArray): String {
        val spec = PBEKeySpec(password.toCharArray(), salt, ITERATIONS, HASH_LENGTH * 8)
        val factory = SecretKeyFactory.getInstance(ALGORITHM)
        val hash = factory.generateSecret(spec).encoded
        return Base64.getEncoder().encodeToString(hash)
    }

    // Генерация соли
    fun generateSalt(): ByteArray {
        val salt = ByteArray(SALT_LENGTH)
        SecureRandom().nextBytes(salt)
        return salt
    }

    // Проверка пароля
    fun verifyPassword(inputPassword: String, storedHash: String, salt: ByteArray): Boolean {
        val hashToVerify = hashPassword(inputPassword, salt)
        return hashToVerify == storedHash
    }
}
