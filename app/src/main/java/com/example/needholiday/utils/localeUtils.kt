package com.example.needholiday.utils

import android.content.Context
import android.content.res.Configuration
import com.example.needholiday.prefs
import java.util.*

fun wrapContext(context: Context) = context.createConfigurationContext(getConfiguration())

fun overrideLocale(context: Context) {
    context.createConfigurationContext(getConfiguration())

    if (context != context.applicationContext) {
        context.applicationContext.run { createConfigurationContext(getConfiguration()) }
    }
}

private fun getConfiguration(): Configuration {
    val savedLocale = Locale(prefs.language)
    Locale.setDefault(savedLocale)

    return Configuration().apply {
        setLocale(savedLocale)
    }
}