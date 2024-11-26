package com.seros.messenger.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.seros.messenger.data.auth.AuthManager
import com.seros.messenger.data.database.entities.MessageEntity
import com.seros.messenger.data.database.entities.MessageStatus
import com.seros.messenger.data.database.entities.MessageType
import com.seros.messenger.domain.usecases.message.GetMessagesForChatUseCase
import com.seros.messenger.domain.usecases.message.SendMessageUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MessageViewModel @Inject constructor(
    private val getMessagesForChatUseCase: GetMessagesForChatUseCase,
    private val sendMessageUseCase: SendMessageUseCase,
    private val authManager: AuthManager
) : ViewModel() {

    private val _messageListState = MutableLiveData<List<MessageEntity>>()
    val messageListState: LiveData<List<MessageEntity>> = _messageListState

    private val _errorState = MutableLiveData<String?>()
    val errorState: LiveData<String?> = _errorState

    private val _sendingMessageState = MutableLiveData<Boolean>()
    val sendingMessageState: LiveData<Boolean> = _sendingMessageState

    fun loadMessagesForChat(chatId: String) {
        viewModelScope.launch {
            try {
                _messageListState.value = getMessagesForChatUseCase(chatId)
            } catch (e: Exception) {
                _errorState.value = e.message
            }
        }
    }

    fun sendMessage(chatId: String, content: String, messageType: MessageType) {
        viewModelScope.launch {
            try {
                _sendingMessageState.value = true
                val senderId = authManager.getUserId()
                val timestamp = System.currentTimeMillis().toString()

                val message = MessageEntity(
                    id = "0",
                    chatId = chatId,
                    senderId = senderId,
                    messageContent = content,
                    messageType = messageType,
                    timestamp = timestamp,
                    status = MessageStatus.PENDING
                )

                sendMessageUseCase(message)
                loadMessagesForChat(chatId)
                _sendingMessageState.value = false
            } catch (e: Exception) {
                _sendingMessageState.value = false
                _errorState.value = e.message
            }
        }
    }
}
