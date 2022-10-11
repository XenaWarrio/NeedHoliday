package com.example.needholiday

import android.app.Application
import com.example.needholiday.repository.SharedPref

open class App : Application() {
    companion object {
        var prefs: SharedPref? = null
    }

    override fun onCreate() {
        super.onCreate()
        prefs = SharedPref(applicationContext)
    }
}

val prefs: SharedPref by lazy {
    App.prefs!!
}