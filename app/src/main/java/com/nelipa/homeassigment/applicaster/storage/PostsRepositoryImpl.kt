package com.nelipa.homeassigment.applicaster.storage

import com.nelipa.homeassigment.applicaster.di.scope.AppScope
import com.nelipa.homeassigment.applicaster.models.PostLinkEntry
import com.nelipa.homeassigment.applicaster.network.ApplicasterApi
import com.nelipa.homeassigment.applicaster.storage.local.PostsDao
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@AppScope
class PostsRepositoryImpl @Inject constructor(
    private val applicasterApi: ApplicasterApi,
    private val postsDao: PostsDao
) : PostsRepository {

    override fun loadPosts() = applicasterApi.getLinks()
        .flatMapCompletable { _posts ->
            postsDao.insertPost(_posts)
        }
        .subscribeOn(Schedulers.io())

    override fun observePosts(): Observable<List<PostLinkEntry>> {
        return postsDao.gePostLinkEntries()
    }

}