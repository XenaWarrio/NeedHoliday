package com.example.needholiday

import android.app.Application
import com.example.needholiday.di.DaggerAppComponent
import com.example.needholiday.repository.QuizRepository
import com.example.needholiday.repository.SharedPref

open class App : Application() {
    companion object {
        var prefs: SharedPref? = null
    }
    val quizRepository by lazy { QuizRepository() }

    override fun onCreate() {
        super.onCreate()
        DaggerAppComponent.create().inject(this)
        prefs = SharedPref(applicationContext)
    }
}

val prefs: SharedPref by lazy {
    App.prefs!!
}