package com.seros.messenger.data.auth


interface AuthManager {
    fun isAuthenticated(): Boolean
    fun login(userId: String, fullName: String)
    fun getUserId(): String
    fun logout()
}
