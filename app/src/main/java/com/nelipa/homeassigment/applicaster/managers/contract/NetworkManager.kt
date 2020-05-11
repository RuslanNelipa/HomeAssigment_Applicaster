package com.nelipa.homeassigment.applicaster.managers.contract

import com.nelipa.homeassigment.applicaster.models.PostEntry
import io.reactivex.Single

interface NetworkManager {
    fun getLinks(): Single<List<PostEntry>>
    fun getVideos(): Single<List<PostEntry>>
}