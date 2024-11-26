package com.seros.messenger.di

import com.seros.messenger.presentation.ui.MainActivity
import dagger.Component

@Component(modules = [AppModule::class])
interface AppComponent {
    fun inject(mainActivity: MainActivity)
}