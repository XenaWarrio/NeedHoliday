package com.example.needholiday.constants

import android.Manifest

const val LANGUAGE_CODE = "language"
const val ANSWERS_COUNT = "3"
const val REQUEST_EXTERNAL_STORAGE = 1
val PERMISSIONS_STORAGE = arrayOf(
    Manifest.permission.READ_EXTERNAL_STORAGE,
    Manifest.permission.WRITE_EXTERNAL_STORAGE
)
