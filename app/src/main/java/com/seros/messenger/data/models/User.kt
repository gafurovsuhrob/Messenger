package com.seros.messenger.data.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class User(
    val id: String = "",
    val fullName: String = "",
    val phoneNumber: String = "",
    val profilePictureUrl: String? = null,
    val password: String = "",
    val salt: String? = null,
    val isOnline: Boolean = false
): Parcelable