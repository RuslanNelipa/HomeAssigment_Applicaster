package com.nelipa.homeassigment.applicaster.storage

import com.nelipa.homeassigment.applicaster.di.scope.AppScope
import com.nelipa.homeassigment.applicaster.managers.NetworkManager
import com.nelipa.homeassigment.applicaster.models.PostEntry
import com.nelipa.homeassigment.applicaster.storage.local.PostsDao
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.rxkotlin.Singles
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@AppScope
class PostsRepositoryImpl @Inject constructor(
        private val networkManager: NetworkManager,
        private val postsDao: PostsDao
) : PostsRepository {

    override fun loadPosts() = Singles.zip(
            networkManager.getLinks(),
            networkManager.getVideos())
            .flatMapCompletable { (_links, _videos) ->
                Completable.concatArray(
                        postsDao.insertAll(_links),
                        postsDao.insertAll(_videos)
                )
            }
            .subscribeOn(Schedulers.io())

    override fun observeAllPosts(): Observable<List<PostEntry>> {
        return postsDao.gePostLinkEntries()
    }
}