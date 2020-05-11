package com.nelipa.homeassigment.applicaster.ext

import androidx.databinding.Observable

fun <T : Observable> T.onPropertyChanged(callback: (T) -> Unit) =
    object : Observable.OnPropertyChangedCallback() {
        override fun onPropertyChanged(observable: Observable?, i: Int) =
            callback(observable as T)
    }.also(::addOnPropertyChangedCallback)