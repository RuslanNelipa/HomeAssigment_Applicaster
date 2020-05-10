package com.nelipa.homeassigment.applicaster.managers

import com.google.gson.internal.LinkedTreeMap
import com.nelipa.homeassigment.applicaster.models.PostEntry
import io.reactivex.Single

interface PostsParser {
    fun parsePosts(raw: LinkedTreeMap<String, Any>): Single<List<PostEntry>>
}