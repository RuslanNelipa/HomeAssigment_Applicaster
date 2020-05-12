package com.nelipa.homeassigment.applicaster.ext

import android.content.Context

fun Context?.getApplicationName(): String = this?.run {
    val applicationInfo = applicationInfo
    val stringId = applicationInfo.labelRes
    return if (stringId == 0) {
        applicationInfo.nonLocalizedLabel.toString()
    } else {
        getString(stringId)
    }
} ?: ""