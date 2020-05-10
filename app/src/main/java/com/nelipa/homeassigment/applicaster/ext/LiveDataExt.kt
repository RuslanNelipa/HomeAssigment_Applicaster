package com.nelipa.homeassigment.applicaster.ext

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.Observer

fun <T> LiveData<T>.observeData(lifecycleOwner: LifecycleOwner, f: (T) -> Unit) {
    this.observe(lifecycleOwner, Observer {
        it?.also(f)
    })
}

inline fun <T> LiveData<T>.filter(crossinline predicate : (T?)->Boolean): LiveData<T> {
    val mutableLiveData: MediatorLiveData<T> = MediatorLiveData()
    mutableLiveData.addSource(this) {
        if(predicate(it))
            mutableLiveData.value = it
    }
    return mutableLiveData
}
