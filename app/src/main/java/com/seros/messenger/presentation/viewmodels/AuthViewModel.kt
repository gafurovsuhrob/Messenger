package com.seros.messenger.presentation.viewmodels

import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.seros.messenger.data.auth.AuthManager
import com.seros.messenger.data.database.entities.UserEntity
import com.seros.messenger.domain.usecases.auth.LoginUserUseCase
import com.seros.messenger.domain.usecases.auth.LogoutUserUseCase
import com.seros.messenger.domain.usecases.auth.RegisterUserUseCase
import com.seros.messenger.domain.usecases.user.GetUserByIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authManager: AuthManager,
    private val getUserByIdUseCase: GetUserByIdUseCase,
    private val loginUserUseCase: LoginUserUseCase,
    private val registerUserUseCase: RegisterUserUseCase,
    private val logoutUserUseCase: LogoutUserUseCase
) : ViewModel() {

    private val _authState = MutableLiveData<FirebaseUser?>()
    val authState: LiveData<FirebaseUser?> = _authState

    private val _userState = MutableLiveData<UserEntity?>()
    val userState: LiveData<UserEntity?> = _userState

    private val _errorState = MutableLiveData<String?>()
    val errorState: LiveData<String?> = _errorState

    private val _loadingState = MutableLiveData<Boolean>()
    val loadingState: LiveData<Boolean> = _loadingState

    init {
        if (authManager.isAuthenticated()) {
            val userId = authManager.getUserId()
            loadUserById(userId)
        }
    }

    private fun loadUserById(userId: String) {
        viewModelScope.launch {
            _loadingState.value = true
            try {
                val user = FirebaseAuth.getInstance().currentUser
                if (user != null) {
                    Log.d("TAG", "loadUserById: $user")
                } else {
                    Log.e("AuthError", "Пользователь не авторизован!")
                }

//                val user = getUserByIdUseCase(userId)
//                _userState.value = user
//                Log.d("TAG", "loadUserById 1: user $user ")
            } catch (e: Exception) {
                _errorState.value = "Ошибка загрузки пользователя 1: ${e.message}"
                Log.d("TAG", "loadUserById 2: ${e.message}")
            } finally {
                _loadingState.value = false
            }
        }
    }

    fun login(phoneNumber: String, password: String) {
        viewModelScope.launch {
            _loadingState.value = true
            try {
                val result = loginUserUseCase(phoneNumber, password)
                if (result.isSuccess) {
                    val firebaseUser = result.getOrNull()
                    if (firebaseUser != null) {
                        _authState.value = firebaseUser
                        authManager.login(firebaseUser.uid, firebaseUser.displayName ?: "")
                        loadUserById(firebaseUser.uid)
                        Log.d("TAG", "login success 1: $firebaseUser")
                    }
                } else {
                    _errorState.value = "Ошибка входа 1: ${result.exceptionOrNull()?.message}"
                    Log.d("TAG", "login error 2: ${result.exceptionOrNull()?.message}")
                }
            } catch (e: Exception) {
                _errorState.value = "Ошибка входа 2: ${e.message}"
                Log.d("TAG", "login error 3: ${e.message}")
            } finally {
                _loadingState.value = false
            }
        }
    }

    fun register(
        fullName: String,
        phoneNumber: String,
        password: String,
        profilePictureUri: Uri?
    ) {
        viewModelScope.launch {
            _loadingState.value = true
            try {
                val result = registerUserUseCase(fullName, phoneNumber, password, profilePictureUri)
                if (result.isSuccess) {
                    val firebaseUser = result.getOrNull()
                    if (firebaseUser != null) {
                        _authState.value = firebaseUser
                        authManager.login(firebaseUser.uid, fullName)
                        loadUserById(firebaseUser.uid)
                        Log.d("TAG", "register 1: ${firebaseUser.phoneNumber}")
                    }
                } else {
                    _errorState.value = "Ошибка регистрации: ${result.exceptionOrNull()?.message}"
                    Log.d("TAG", "register 2: ${result.exceptionOrNull()?.message}")
                }
            } catch (e: Exception) {
                _errorState.value = "Ошибка регистрации: ${e.message}"
                Log.d("TAG", "register 3: ${e.message}")
            } finally {
                _loadingState.value = false
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            _loadingState.value = true
            try {
                logoutUserUseCase()
                authManager.logout()
                _authState.value = null
                _userState.value = null
            } catch (e: Exception) {
                _errorState.value = "Ошибка выхода: ${e.message}"
                Log.d("TAG", "logout 1: ${e.message}")
            } finally {
                _loadingState.value = false
            }
        }
    }

    fun clearError() {
        _errorState.value = null
    }
}
