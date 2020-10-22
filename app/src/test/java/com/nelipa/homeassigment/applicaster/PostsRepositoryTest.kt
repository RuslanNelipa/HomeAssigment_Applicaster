package com.nelipa.homeassigment.applicaster

import com.nelipa.homeassigment.applicaster.managers.contract.NetworkManager
import com.nelipa.homeassigment.applicaster.models.MediaGroup
import com.nelipa.homeassigment.applicaster.models.PostEntry
import com.nelipa.homeassigment.applicaster.models.PostType
import com.nelipa.homeassigment.applicaster.storage.PostsRepository
import com.nelipa.homeassigment.applicaster.storage.PostsRepositoryImpl
import com.nelipa.homeassigment.applicaster.storage.local.PostsDao
import com.nhaarman.mockitokotlin2.capture
import com.nhaarman.mockitokotlin2.firstValue
import com.nhaarman.mockitokotlin2.secondValue
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.subjects.PublishSubject
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.ArgumentCaptor
import org.mockito.ArgumentMatchers
import org.mockito.Captor
import org.mockito.Mockito.*

class PostsRepositoryTest : BaseUnitTest() {
    private lateinit var postsRepository: PostsRepositoryImpl

    private val networkManager = mock(NetworkManager::class.java)
    private val postsDao = mock(PostsDao::class.java)

    private val linksSuccessSingle = Single.create<List<PostEntry>>{
        it.onSuccess(listOf(
            PostEntry("1", "2", "3", "4", "5", PostType.LINK, "7", mock(MediaGroup::class.java)),
            PostEntry("1", "2", "3", "4", "5", PostType.LINK, "7", mock(MediaGroup::class.java))
        ))
    }

    private val videoSuccessSingle = Single.create<List<PostEntry>>{
        it.onSuccess(listOf(
            PostEntry("1", "2", "3", "4", "5", PostType.VIDEO, "7", mock(MediaGroup::class.java)),
            PostEntry("1", "2", "3", "4", "5", PostType.VIDEO, "7", mock(MediaGroup::class.java))
        ))
    }
    private val completeAction = Completable.complete()

    @Captor
    lateinit var postsCaptor: ArgumentCaptor<List<PostEntry>>

    override fun doBefore() {
        postsRepository = spy(PostsRepositoryImpl(networkManager, postsDao))
    }

    @Test
    fun `load posts fetches data from network manager and saves to local DB`(){
        doReturn(linksSuccessSingle).`when`(networkManager).getLinks()
        doReturn(videoSuccessSingle).`when`(networkManager).getVideos()
        doReturn(completeAction).`when`(postsDao).insertAll(capture(postsCaptor))

        postsRepository.loadPosts().test().awaitCount(1)

        assertEquals(postsCaptor.firstValue.size, 2)
        assertEquals(postsCaptor.firstValue.first().type, PostType.LINK)
        assertEquals(postsCaptor.secondValue.size, 2)
        assertEquals(postsCaptor.secondValue.first().type, PostType.VIDEO)
    }
}