package com.seros.messenger.data.auth

import android.content.Context
import android.content.SharedPreferences
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject


class AuthManagerImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : AuthManager {

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("auth_preferences", Context.MODE_PRIVATE)

    companion object {
        private const val USER_ID_KEY = "user_id"
        private const val USER_NAME_KEY = "user_name"
    }

    override fun isAuthenticated(): Boolean {
        return sharedPreferences.contains(USER_ID_KEY)
    }

    override fun login(userId: String, fullName: String) {
        sharedPreferences.edit()
            .putString(USER_ID_KEY, userId)
            .putString(USER_NAME_KEY, fullName)
            .apply()
    }

    override fun logout() {
        sharedPreferences.edit().clear().apply()
    }

    override fun getUserId(): String {
        return sharedPreferences.getString(USER_ID_KEY, "-1").toString()
    }

    fun getUserName(): String? {
        return sharedPreferences.getString(USER_NAME_KEY, null)
    }
}
