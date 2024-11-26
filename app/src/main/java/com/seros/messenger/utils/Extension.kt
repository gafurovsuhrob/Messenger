package com.seros.messenger.utils


import android.annotation.SuppressLint
import android.content.ContentResolver
import android.content.Context
import android.provider.ContactsContract
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.google.android.material.textfield.TextInputLayout
import com.seros.messenger.data.models.Contact

/**
 * Загружает список контактов с телефона.
 */
fun Context.loadContacts(): List<Contact> {
    val contacts = mutableListOf<Contact>()
    val contentResolver: ContentResolver = this.contentResolver
    val uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI
    val projection = arrayOf(
        ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
        ContactsContract.CommonDataKinds.Phone.NUMBER
    )

    val cursor = contentResolver.query(uri, projection, null, null, null)
    cursor?.use {
        val nameIndex = it.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)
        val numberIndex = it.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)

        while (it.moveToNext()) {
            var number = it.getString(numberIndex) ?: ""
            number = number.normalizePhoneNumber()
            val name = (it.getString(nameIndex) ?: "").normalizePhoneNumber()

            if (number.isNotEmpty()) {
                contacts.add(
                    Contact(
                        name = name,
                        number = number,
                        isInSystem = false
                    )
                )
            }
        }
    }
    return contacts
}


/**
 * Нормализует номер телефона: добавляет +992, если его нет, и очищает от лишних символов.
 */
fun String.normalizePhoneNumber(): String {
    val cleanedNumber = this.replace("[^\\d+]".toRegex(), "")

    return if (cleanedNumber.length == 9 && !cleanedNumber.startsWith("+")) {
        "+992$cleanedNumber"
    } else if (cleanedNumber.startsWith("+992") && cleanedNumber.length == 13) {
        cleanedNumber
    } else {
        this
    }
}

/**
 * Скрывает клавиатуру при нажатии на любую часть экрана
 */
@SuppressLint("ClickableViewAccessibility")
fun setupClearErrorOnTouch(editTextList: List<TextInputLayout>, mainLayouts: List<View>) {
    mainLayouts.forEach { mainLayout ->
        mainLayout.setOnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_DOWN) {
                val imm =
                    mainLayout.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(mainLayout.windowToken, 0)
                editTextList.forEach {
                    it.clearFocus()
                    it.error = null
                    it.isErrorEnabled = false
                }
            }
            false
        }

        editTextList.forEach {
            it.setOnTouchListener { _, event ->
                if (event.action == MotionEvent.ACTION_DOWN) {
                    val imm =
                        mainLayout.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(mainLayout.windowToken, 0)
                    it.clearFocus()
                    it.error = null
                    it.isErrorEnabled = false
                }
                false
            }
        }
    }
}



