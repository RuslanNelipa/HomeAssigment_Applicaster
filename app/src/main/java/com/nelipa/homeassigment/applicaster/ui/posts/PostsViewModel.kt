package com.nelipa.homeassigment.applicaster.ui.posts

import android.webkit.URLUtil
import androidx.databinding.ObservableBoolean
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.nelipa.homeassigment.applicaster.base.BaseViewModel
import com.nelipa.homeassigment.applicaster.managers.contract.SearchQueryProvider
import com.nelipa.homeassigment.applicaster.managers.implementation.SearchQueryAdapter
import com.nelipa.homeassigment.applicaster.models.PostEntry
import com.nelipa.homeassigment.applicaster.models.PostItem
import com.nelipa.homeassigment.applicaster.models.PostType
import com.nelipa.homeassigment.applicaster.storage.PostsRepository
import com.nelipa.homeassigment.applicaster.utils.Event
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class PostsViewModel @Inject constructor(
    private val postsRepo: PostsRepository,
    private val searchQueryAdapter: SearchQueryAdapter
) : BaseViewModel() {

    val emptySearchResultsVisible = ObservableBoolean(false)

    private val isLoadingMutableLiveData = MutableLiveData(false)
    private val errorMutableLiveData = MutableLiveData<Event<PostsError>>()
    private val postsMutableLiveData = MutableLiveData<List<Any>>()
    private val linkPostClickedMutableLiveData = MutableLiveData<Event<PostEntry>>()
    private val videoPostClickedMutableLiveData = MutableLiveData<Event<PostEntry>>()

    init {
        loadPosts()
        observePosts()
    }

    fun errorLiveData(): LiveData<Event<PostsError>> = errorMutableLiveData
    fun postsLiveData(): LiveData<List<Any>> = postsMutableLiveData
    fun isLoadingLiveData(): LiveData<Boolean> = isLoadingMutableLiveData
    fun linkPostClickedLiveData(): LiveData<Event<PostEntry>> = linkPostClickedMutableLiveData
    fun videoPostClickedLiveData(): LiveData<Event<PostEntry>> = videoPostClickedMutableLiveData
    fun searchQueryProvider(): SearchQueryProvider = searchQueryAdapter

    fun refreshPosts() = postsRepo.refreshPosts()
        .doOnSubscribe { isLoadingMutableLiveData.postValue(true) }
        .subscribeBy(
            onError = {
                errorMutableLiveData.postValue(Event(PostsError.NetworkError))
            }
        )
        .addToCompositeDisposable()

    fun loadPosts() = postsRepo.loadPosts()
        .doOnSubscribe { isLoadingMutableLiveData.postValue(true) }
        .subscribeBy(
            onError = {
                errorMutableLiveData.postValue(Event(PostsError.NetworkError))
            }
        )
        .addToCompositeDisposable()

    private fun observePosts() {
        searchQueryAdapter.observeSearchQuery()
            .flatMap(::getProperPostsObserver)
            .map { it.toPostItems() }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onNext = { _items ->
                    postsMutableLiveData.value = _items
                    isLoadingMutableLiveData.value = false
                },
                onError = {
                    errorMutableLiveData.postValue(Event(PostsError.Generic("Failed to retrieve data")))
                }
            )
            .addToCompositeDisposable()
    }

    private fun getProperPostsObserver(query: String) = if (query.isEmpty())
        postsRepo.observeAllPosts()
            .doOnNext { emptySearchResultsVisible.set(false) }
    else
        postsRepo.observePostsFilteredBy(query)
            .doOnNext { _items -> emptySearchResultsVisible.set(_items.isEmpty()) }

    private fun List<PostEntry>.toPostItems(): List<Any> = map { _postEntry ->
        when (_postEntry.type) {
            PostType.LINK -> PostItem.PostLinkItem(_postEntry, ::onPostLinkClicked)
            PostType.VIDEO -> PostItem.PostVideoItem(_postEntry, ::onPostVideoClicked)
            PostType.UNKNOWN -> PostItem.PostUnsupportedItem
        }
    }

    private fun onPostLinkClicked(postLink: PostEntry) {
        if (URLUtil.isValidUrl(postLink.link))
            linkPostClickedMutableLiveData.value = Event(postLink)
        else
            errorMutableLiveData.value = Event(PostsError.InvalidUrl(postLink.link))
    }

    private fun onPostVideoClicked(postLink: PostEntry) {
        videoPostClickedMutableLiveData.value = Event(postLink)
    }

    sealed class PostsError {
        object NetworkError : PostsError()
        class InvalidUrl(val url: String?) : PostsError()
        class Generic(val message: String) : PostsError()
    }
}