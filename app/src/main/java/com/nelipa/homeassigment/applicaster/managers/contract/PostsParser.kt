package com.nelipa.homeassigment.applicaster.managers.contract

import com.google.gson.internal.LinkedTreeMap
import com.nelipa.homeassigment.applicaster.models.PostEntry
import io.reactivex.Single

interface PostsParser {
    /**
     * Parse posts response and map into valid DTO
     * @param raw is the raw network response entity
     * @return Single with mapped list of posts
     * */
    fun parsePosts(raw: LinkedTreeMap<String, Any>): Single<List<PostEntry>>
}