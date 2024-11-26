package com.seros.messenger.data.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Contact(
    val name: String?,
    val number: String,
    val isInSystem: Boolean
): Parcelable
