package com.nelipa.homeassigment.applicaster.storage

import com.nelipa.homeassigment.applicaster.models.PostEntry
import io.reactivex.Completable
import io.reactivex.Observable

interface PostsRepository {
    /**
     * Takes fresh values from API
     * @return Completable once data is fetched and saved to local DB
     * */
    fun loadPosts(): Completable

    /**
     * Removes all entries from DB and fetches new from API
     * @return Completable once data is fetched and saved to local DB
     * */
    fun refreshPosts(): Completable

    /**
     * Observe for all saved Posts
     * @return Observable with list of post entries
     * */
    fun observeAllPosts(): Observable<List<PostEntry>>

    /**
     * Observe for all saved Posts filtered by query
     * @param query is keyword to search
     * @return Observable with filtered list of post entries
     * */
    fun observePostsFilteredBy(query: String): Observable<List<PostEntry>>
}