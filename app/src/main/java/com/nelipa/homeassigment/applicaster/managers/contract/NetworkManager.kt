package com.nelipa.homeassigment.applicaster.managers.contract

import com.nelipa.homeassigment.applicaster.models.PostEntry
import io.reactivex.Single

interface NetworkManager {
    /**
     * Loads all link type posts from API
     * @return Single with link posts
     * */
    fun getLinks(): Single<List<PostEntry>>

    /**
     * Loads all video type posts from API
     * @return Single with link posts
     * */
    fun getVideos(): Single<List<PostEntry>>
}