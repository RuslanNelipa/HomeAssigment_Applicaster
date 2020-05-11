package com.nelipa.homeassigment.applicaster.managers.implementation

import com.nelipa.homeassigment.applicaster.managers.contract.NetworkManager
import com.nelipa.homeassigment.applicaster.managers.contract.PostsParser
import com.nelipa.homeassigment.applicaster.storage.remote.ApplicasterApi
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class NetworkManagerImpl @Inject constructor(
        private val api: ApplicasterApi,
        private val parser: PostsParser
) : NetworkManager {

    override fun getLinks() = api.getLinks()
            .subscribeOn(Schedulers.io())
            .flatMap(parser::parsePosts)
            .subscribeOn(Schedulers.computation())

    override fun getVideos() = api.getVideos()
            .subscribeOn(Schedulers.io())
            .flatMap(parser::parsePosts)
            .subscribeOn(Schedulers.computation())
}