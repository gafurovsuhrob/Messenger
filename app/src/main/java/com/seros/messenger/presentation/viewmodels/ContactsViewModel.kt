package com.seros.messenger.presentation.viewmodels

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.seros.messenger.data.models.Contact
import com.seros.messenger.utils.loadContacts
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ContactsViewModel @Inject constructor(): ViewModel() {

    private val _contacts = MutableLiveData<List<Contact>>()
    val contacts: LiveData<List<Contact>> get() = _contacts

    private val _contactsInSystem = MutableLiveData<List<Contact>>()
    val contactsInSystem: LiveData<List<Contact>> get() = _contactsInSystem

    private val _contactsNotInSystem = MutableLiveData<List<Contact>>()
    val contactsNotInSystem: LiveData<List<Contact>> get() = _contactsNotInSystem

    fun loadContactsFromDevice(context: Context) {
        val contacts = context.loadContacts()
        _contacts.value = contacts
    }

    fun filterContactsInSystem(systemUsers: List<String>) {
        val contactsInSystem = _contacts.value?.filter { contact ->
            systemUsers.contains(contact.number)
        }
        _contactsInSystem.value = contactsInSystem ?: emptyList()

        val contactsNotInSystem = _contacts.value?.filter { contact ->
            !systemUsers.contains(contact.number)
        }
        _contactsNotInSystem.value = contactsNotInSystem ?: emptyList()
    }
}