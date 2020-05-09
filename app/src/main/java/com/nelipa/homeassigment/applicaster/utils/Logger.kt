package com.nelipa.homeassigment.applicaster.utils

import timber.log.Timber

fun logd(message: String) {
    Timber.d(message)
}

fun loge(e: Throwable, message: String) {
    Timber.e(e, message)
}