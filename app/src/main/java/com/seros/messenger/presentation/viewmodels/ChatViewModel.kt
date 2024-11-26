package com.seros.messenger.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.seros.messenger.data.auth.AuthManager
import com.seros.messenger.data.database.entities.ChatEntity
import com.seros.messenger.data.database.entities.MessageEntity
import com.seros.messenger.domain.repositories.ChatRepository
import com.seros.messenger.domain.usecases.chat.CreateChatUseCase
import com.seros.messenger.domain.usecases.chat.GetChatsForUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val getChatsForUserUseCase: GetChatsForUserUseCase,
    private val createChatUseCase: CreateChatUseCase,
    private val authManager: AuthManager
) : ViewModel() {

    private val _chatListState = MutableLiveData<List<ChatEntity>>()
    val chatListState: LiveData<List<ChatEntity>> = _chatListState

    private val _errorState = MutableLiveData<String?>()
    val errorState: LiveData<String?> = _errorState

    init {
        loadChatsForCurrentUser()
    }

    private fun loadChatsForCurrentUser() {
        viewModelScope.launch {
            try {
                if (authManager.isAuthenticated()) {
                    val userId = authManager.getUserId()
                    _chatListState.value = getChatsForUserUseCase(userId)
                }
            } catch (e: Exception) {
                _errorState.value = e.message
            }
        }
    }

    fun createChat(chat: ChatEntity) {
        viewModelScope.launch {
            try {
                createChatUseCase(chat)
                loadChatsForCurrentUser()
            } catch (e: Exception) {
                _errorState.value = e.message
            }
        }
    }
}
