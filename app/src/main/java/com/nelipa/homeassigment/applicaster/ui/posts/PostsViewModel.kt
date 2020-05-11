package com.nelipa.homeassigment.applicaster.ui.posts

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.nelipa.homeassigment.applicaster.base.BaseViewModel
import com.nelipa.homeassigment.applicaster.models.PostEntry
import com.nelipa.homeassigment.applicaster.models.PostItem
import com.nelipa.homeassigment.applicaster.models.PostType
import com.nelipa.homeassigment.applicaster.storage.PostsRepository
import com.nelipa.homeassigment.applicaster.utils.Event
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class PostsViewModel @Inject constructor(
    private val postsRepo: PostsRepository
) : BaseViewModel() {

    private val isLoadingMutableLiveData = MutableLiveData(false)
    private val errorMutableLiveData = MutableLiveData<PostsError>()
    private val postsMutableLiveData = MutableLiveData<List<Any>>()
    private val linkPostClickedMutableLiveData = MutableLiveData<Event<PostEntry>>()
    private val videoPostClickedMutableLiveData = MutableLiveData<Event<PostEntry>>()

    init {
        loadPosts()
        observePosts()
    }

    fun errorLiveData(): LiveData<PostsError> = errorMutableLiveData
    fun postsLiveData(): LiveData<List<Any>> = postsMutableLiveData
    fun isLoadingLiveData(): LiveData<Boolean> = isLoadingMutableLiveData
    fun linkPostClickedLiveData(): LiveData<Event<PostEntry>> = linkPostClickedMutableLiveData
    fun videoPostClickedLiveData(): LiveData<Event<PostEntry>> = videoPostClickedMutableLiveData

    private fun loadPosts() = postsRepo.loadPosts()
        .doOnSubscribe { isLoadingMutableLiveData.postValue(true) }
        .subscribeBy(
            onError = {
                errorMutableLiveData.postValue(PostsError.NetworkError("Failed to load posts. Please check you Internet Connection"))
            }
        )
        .addToCompositeDisposable()

    private fun observePosts() = postsRepo.observeAllPosts()
        .filter { it.isNotEmpty() }
        .map { it.toPostItems() }
        .subscribeOn(Schedulers.computation())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeBy(
            onNext = { _items ->
                postsMutableLiveData.value = _items
                isLoadingMutableLiveData.value = false
            },
            onError = {
                errorMutableLiveData.postValue(PostsError.Unknown("Failed to retrieve data"))
            }
        )
        .addToCompositeDisposable()

    private fun List<PostEntry>.toPostItems(): List<Any> = map { _postEntry ->
        when (_postEntry.type) {
            PostType.LINK -> PostItem.PostLinkItem(_postEntry, ::onPostLinkClicked)
            PostType.VIDEO -> PostItem.PostVideoItem(_postEntry, ::onPostVideoClicked)
            PostType.UNKNOWN -> PostItem.PostUnsupportedItem
        }
    }

    private fun onPostLinkClicked(postLink: PostEntry) =
        linkPostClickedMutableLiveData.postValue(Event(postLink))

    private fun onPostVideoClicked(postLink: PostEntry) =
        videoPostClickedMutableLiveData.postValue(Event(postLink))

    sealed class PostsError(val message: String) {
        class NetworkError(message: String) : PostsError(message)
        class Unknown(message: String) : PostsError(message)
    }
}