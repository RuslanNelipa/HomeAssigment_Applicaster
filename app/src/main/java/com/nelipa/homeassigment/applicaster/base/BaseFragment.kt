package com.nelipa.homeassigment.applicaster.base

import android.content.Context
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

abstract class BaseFragment : Fragment(), HasAndroidInjector {
    @Inject
    lateinit var androidInjector: DispatchingAndroidInjector<Any>

    @Inject
    open lateinit var viewModelFactory: ViewModelProvider.Factory

    private var backClickedAction: (() -> Unit)? = null

    private val backClickCallback by lazy {
        object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                isEnabled = false
                backClickedAction?.invoke() ?: activity?.onBackPressed()
            }
        }
    }

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    fun overrideBackClick(onBackClickedAction: () -> Unit) = activity?.run {
        backClickedAction = onBackClickedAction
        onBackPressedDispatcher.addCallback(this, backClickCallback)
    }

    override fun androidInjector(): AndroidInjector<Any> = androidInjector

    inline fun <reified VM : ViewModel> Fragment.viewModels() = viewModels<VM> { viewModelFactory }
}