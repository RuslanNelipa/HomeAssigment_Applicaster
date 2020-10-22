package com.nelipa.homeassigment.applicaster.base

import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.plugins.RxJavaPlugins
import timber.log.Timber

abstract class BaseViewModel : ViewModel() {
    private val compositeDisposable = CompositeDisposable()

    init {
       overrideRxErrorsBehavior()
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }

    fun Disposable?.addToCompositeDisposable() {
        compositeDisposable.addAll(this)
    }

    private fun overrideRxErrorsBehavior() {
        RxJavaPlugins.setErrorHandler { e -> e.stackTrace }
    }
}