package com.nelipa.homeassigment.applicaster.ext

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

fun <T> LiveData<T>.observeData(lifecycleOwner: LifecycleOwner, f: (T) -> Unit) {
    this.observe(lifecycleOwner, Observer {
        it?.also(f)
    })
}