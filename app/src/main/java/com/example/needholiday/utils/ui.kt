package com.example.needholiday.utils

import android.view.View
import androidx.core.view.isVisible

fun View.visible() {
    if (visibility == View.INVISIBLE || visibility == View.GONE) visibility = View.VISIBLE
}

fun View.invisible() {
    if (isVisible) visibility = View.INVISIBLE
}

fun View.gone() {
    if (isVisible) visibility = View.GONE
}