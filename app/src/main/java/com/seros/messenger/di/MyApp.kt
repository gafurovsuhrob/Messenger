package com.seros.messenger.di

import android.app.Application
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class MyApp: Application() {

    override fun onCreate() {
        super.onCreate()

    }
}