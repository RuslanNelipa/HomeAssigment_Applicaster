package com.nelipa.homeassigment.applicaster.storage

import com.nelipa.homeassigment.applicaster.models.PostLinkEntry
import io.reactivex.Completable
import io.reactivex.Observable


//todo add docs
interface PostsRepository {
    fun loadPosts(): Completable
    fun observePosts(): Observable<List<PostLinkEntry>>
}