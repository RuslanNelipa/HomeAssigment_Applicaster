package com.nelipa.homeassigment.applicaster.ui.posts

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.nelipa.homeassigment.applicaster.base.BaseViewModel
import com.nelipa.homeassigment.applicaster.models.PostEntry
import com.nelipa.homeassigment.applicaster.storage.PostsRepository
import io.reactivex.rxkotlin.subscribeBy
import javax.inject.Inject

class PostsViewModel @Inject constructor(
    private val postsRepo: PostsRepository
) : BaseViewModel() {

    private val loadingMutableLiveData = MutableLiveData<Boolean>()
    private val errorMutableLiveData = MutableLiveData<PostsError>()
    private val postsMutableLiveData = MutableLiveData<List<PostEntry>>()

    init {
        loadPosts()
        observePosts()
    }

    fun loadingLiveData(): LiveData<Boolean> = loadingMutableLiveData
    fun errorLiveData(): LiveData<PostsError> = errorMutableLiveData
    fun postsLiveData(): LiveData<List<PostEntry>> = postsMutableLiveData

    private fun loadPosts() = postsRepo.loadPosts()
        .doOnSubscribe { loadingMutableLiveData.postValue(true) }
        .subscribeBy(
            onError = {
                errorMutableLiveData.postValue(PostsError.NetworkError("Failed to load posts. Please check you Internet Connection"))
            }
        )
        .addToCompositeDisposable()

    private fun observePosts() = postsRepo.observeAllPosts()
        .subscribeBy(
            onNext = {
                loadingMutableLiveData.postValue(false)
            },
            onError = {
                errorMutableLiveData.postValue(PostsError.Unknown("Failed to retrieve data"))
            }
        )
        .addToCompositeDisposable()

    sealed class PostsError(val message: String) {
        class NetworkError(message: String) : PostsError(message)
        class Unknown(message: String) : PostsError(message)
    }
}