package com.seros.messenger.presentation.viewmodels


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.seros.messenger.data.auth.AuthManager
import com.seros.messenger.data.database.entities.UserEntity
import com.seros.messenger.domain.usecases.user.GetUserByIdUseCase
import com.seros.messenger.domain.usecases.user.UpdateUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val getUserByIdUseCase: GetUserByIdUseCase,
    private val updateUserUseCase: UpdateUserUseCase,
    private val authManager: AuthManager
) : ViewModel() {

    private val _userState = MutableLiveData<UserEntity?>()
    val userState: LiveData<UserEntity?> = _userState

    private val _errorState = MutableLiveData<String?>()
    val errorState: LiveData<String?> = _errorState

    fun loadUser() {
        viewModelScope.launch {
            try {
                if (authManager.isAuthenticated()) {
                    val userId = authManager.getUserId()
                    _userState.value = getUserByIdUseCase(userId)
                }
            } catch (e: Exception) {
                _errorState.value = e.message
            }
        }
    }

    fun updateUser(user: UserEntity) {
        viewModelScope.launch {
            try {
                updateUserUseCase(user)
                _userState.value = user
            } catch (e: Exception) {
                _errorState.value = e.message
            }
        }
    }
}
