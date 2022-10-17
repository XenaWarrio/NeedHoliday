package com.example.needholiday.utils

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import com.example.needholiday.constants.PERMISSIONS_STORAGE
import com.example.needholiday.constants.REQUEST_EXTERNAL_STORAGE

fun Activity.verifyStoragePermissions() {
    val permission = ActivityCompat.checkSelfPermission(
        this,
        Manifest.permission.WRITE_EXTERNAL_STORAGE
    )
    if (permission != PackageManager.PERMISSION_GRANTED) {
        ActivityCompat.requestPermissions(
            this,
            PERMISSIONS_STORAGE,
            REQUEST_EXTERNAL_STORAGE
        )
    }
}