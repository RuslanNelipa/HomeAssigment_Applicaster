package com.nelipa.homeassigment.applicaster.custom

import android.view.View
import androidx.databinding.BindingAdapter

@BindingAdapter("visibleOrGone")
fun View.setVisibleOrGone(show: Boolean) {
    visibility = if (show) View.VISIBLE else View.GONE
}