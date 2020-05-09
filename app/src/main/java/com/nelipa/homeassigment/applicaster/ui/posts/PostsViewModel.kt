package com.nelipa.homeassigment.applicaster.ui.posts

import com.nelipa.homeassigment.applicaster.base.BaseViewModel
import com.nelipa.homeassigment.applicaster.storage.PostsRepository
import com.nelipa.homeassigment.applicaster.utils.logd
import com.nelipa.homeassigment.applicaster.utils.loge
import io.reactivex.rxkotlin.subscribeBy
import javax.inject.Inject

class PostsViewModel @Inject constructor(
    private val postsRepo: PostsRepository
) : BaseViewModel() {

    fun loadPosts() = postsRepo.loadPosts()
        .subscribeBy(
            onComplete = {
                logd("Posts saved")
            },

            onError = {
                loge(it, "Failed to save posts")
            }
        )
        .addToCompositeDisposable()
}