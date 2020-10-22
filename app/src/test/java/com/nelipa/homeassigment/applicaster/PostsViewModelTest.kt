package com.nelipa.homeassigment.applicaster

import com.nelipa.homeassigment.applicaster.managers.implementation.SearchQueryAdapter
import com.nelipa.homeassigment.applicaster.models.MediaGroup
import com.nelipa.homeassigment.applicaster.models.PostEntry
import com.nelipa.homeassigment.applicaster.models.PostType
import com.nelipa.homeassigment.applicaster.storage.PostsRepository
import com.nelipa.homeassigment.applicaster.ui.posts.PostsViewModel
import com.nhaarman.mockitokotlin2.anyOrNull
import io.reactivex.Completable
import io.reactivex.subjects.PublishSubject
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.*


class PostsViewModelTest : BaseUnitTest(){
    //mock all dependencies
    private val postsRepository = mock(PostsRepository::class.java)
    private val searchQueryAdapter = mock(SearchQueryAdapter::class.java)

    //spy tested entity
    private lateinit var viewModel: PostsViewModel

    private val postsSubject = PublishSubject.create<List<PostEntry>>()
    private val filteredSubject = PublishSubject.create<List<PostEntry>>()
    private val searchQuerySubject = PublishSubject.create<String>()
    private val postsLoaded = Completable.complete()

    override fun doBefore() {
        doReturn(postsLoaded).`when`(postsRepository).loadPosts()
        doReturn(searchQuerySubject).`when`(searchQueryAdapter).observeSearchQuery()
        doReturn(postsSubject).`when`(postsRepository).observeAllPosts()
        doReturn(filteredSubject).`when`(postsRepository).observePostsFilteredBy(anyOrNull())

        viewModel = spy(PostsViewModel(postsRepository, searchQueryAdapter))
    }

    @Test
    fun `emitted posts from repository should be delivered to UI my live data`() {
        searchQuerySubject.onNext("")
        postsSubject.onNext(
            listOf(
                PostEntry("1", "2", "3", "4", "5", PostType.LINK, "7", mock(MediaGroup::class.java)),
                PostEntry("1", "2", "3", "4", "5", PostType.VIDEO, "7", mock(MediaGroup::class.java)),
                PostEntry("1", "2", "3", "4", "5", PostType.LINK, "7", mock(MediaGroup::class.java)),
                PostEntry("1", "2", "3", "4", "5", PostType.VIDEO, "7", mock(MediaGroup::class.java))
            )
        )

        assertEquals(viewModel.postsLiveData().value!!.size, 4)
    }

    @Test
    fun `when search query is entered items are emitted from search subject`() {
        searchQuerySubject.onNext("1")
        postsSubject.onNext( //should be ignored
            listOf(
                PostEntry("1", "2", "3", "4", "5", PostType.LINK, "7", mock(MediaGroup::class.java)),
                PostEntry("1", "2", "3", "4", "5", PostType.VIDEO, "7", mock(MediaGroup::class.java)),
                PostEntry("1", "2", "3", "4", "5", PostType.LINK, "7", mock(MediaGroup::class.java))
            )
        )

        filteredSubject.onNext(
            listOf(
                PostEntry("1", "2", "3", "4", "5", PostType.LINK, "7", mock(MediaGroup::class.java)),
                PostEntry("1", "2", "3", "4", "5", PostType.VIDEO, "7", mock(MediaGroup::class.java))
            )
        )

        assertEquals(viewModel.postsLiveData().value!!.size, 2)
    }
}
