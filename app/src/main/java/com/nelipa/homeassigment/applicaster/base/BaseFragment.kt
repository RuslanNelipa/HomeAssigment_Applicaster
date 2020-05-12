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

    private var backClickedAction: (() -> Boolean)? = null

    private val backClickCallback by lazy {
        object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                val isHandled = backClickedAction?.invoke() ?: false

                if (isEnabled && !isHandled) {
                    isEnabled = false
                    activity?.onBackPressed()
                }
            }
        }
    }

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun androidInjector(): AndroidInjector<Any> = androidInjector

    fun overrideBackClick(onBackClickedAction: () -> Boolean) = activity?.run {
        backClickedAction = onBackClickedAction
        onBackPressedDispatcher.addCallback(this, backClickCallback)
    }

    inline fun <reified VM : ViewModel> Fragment.viewModels() = viewModels<VM> { viewModelFactory }
}