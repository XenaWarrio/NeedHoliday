package com.example.needholiday.di

import com.example.needholiday.App
import dagger.Component

@Component
interface AppComponent {
    fun inject (app: App)
}