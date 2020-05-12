package com.nelipa.homeassigment.applicaster.ext

import android.view.View
import com.google.android.material.snackbar.Snackbar

fun View.snack(
    message: String,
    actionText: String,
    duration: Int = Snackbar.LENGTH_INDEFINITE,
    onClick: (View) -> Unit
) = Snackbar.make(this, message, duration)
    .setAction(actionText, onClick)

fun View.snack(
    message: String,
    duration: Int = Snackbar.LENGTH_LONG
) = Snackbar.make(this, message, duration)