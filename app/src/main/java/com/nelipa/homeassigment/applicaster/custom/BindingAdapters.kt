package com.nelipa.homeassigment.applicaster.custom

import android.view.View
import android.webkit.URLUtil
import android.webkit.WebView
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.nelipa.homeassigment.applicaster.R


@BindingAdapter("visibleOrGone")
fun View.setVisibleOrGone(show: Boolean) {
    visibility = if (show) View.VISIBLE else View.GONE
}

@BindingAdapter("src_url")
fun setImageUrl(view: ImageView, url: String?) {
    url?.let {
        if (URLUtil.isValidUrl(url)) {
            Glide.with(view.context).load(url).into(view)
        } else {
            view.setImageResource(R.drawable.ic_broken_image)
        }
    }
}

@BindingAdapter("onClickCallLambda")
fun View.setOnClick(onClick: Runnable) {
    this.setOnClickListener {
        onClick.run()
    }
}

@BindingAdapter("loadUrl")
fun WebView.loadWebViewUrl(url: String) {
    if (URLUtil.isValidUrl(url)) {
        loadUrl(url)
    }
}