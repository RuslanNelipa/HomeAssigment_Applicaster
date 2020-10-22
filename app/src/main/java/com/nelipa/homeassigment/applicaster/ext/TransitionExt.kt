package com.nelipa.homeassigment.applicaster.ext

import androidx.transition.Transition

fun Transition.onTransition(
    onEnd: (() -> Unit)? = null,
    onResume: (() -> Unit)? = null,
    onPause: (() -> Unit)? = null,
    onCancel: (() -> Unit)? = null,
    onStart: (() -> Unit)? = null
) {
    addListener(object : Transition.TransitionListener {
        override fun onTransitionEnd(transition: Transition) {
            onEnd?.invoke()
        }

        override fun onTransitionResume(transition: Transition) {
            onResume?.invoke()
        }

        override fun onTransitionPause(transition: Transition) {
            onPause?.invoke()
        }

        override fun onTransitionCancel(transition: Transition) {
            onCancel?.invoke()
        }

        override fun onTransitionStart(transition: Transition) {
            onStart?.invoke()
        }
    })
}