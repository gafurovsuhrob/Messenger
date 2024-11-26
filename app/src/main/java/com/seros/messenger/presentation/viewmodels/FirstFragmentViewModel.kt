package com.seros.messenger.presentation.viewmodels

import androidx.lifecycle.ViewModel
import com.seros.messenger.data.auth.AuthManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FirstFragmentViewModel @Inject constructor(
    private val authManager: AuthManager
) : ViewModel() {

    fun isAuthenticated(): Boolean {
        return authManager.isAuthenticated()
    }

    fun login(userId: String, fullName: String) {
        authManager.login(userId, fullName)
    }

    fun logout() {
        authManager.logout()
    }
}