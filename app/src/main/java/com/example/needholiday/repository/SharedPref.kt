package com.example.needholiday.repository

import android.content.Context
import android.content.SharedPreferences
import com.example.needholiday.constants.LANGUAGE_CODE


class SharedPref(context: Context) {
    private val fileName = "NeedHolidayPreferences"
    private val preferences: SharedPreferences =
        context.getSharedPreferences(fileName, Context.MODE_PRIVATE)

    var language: String
        get() = preferences.getString(LANGUAGE_CODE, null) ?: "en"
        set(value) = preferences.edit().putString(LANGUAGE_CODE, value).apply()
}

