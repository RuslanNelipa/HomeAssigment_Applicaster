package com.nelipa.homeassigment.applicaster.storage.remote

import com.google.gson.internal.LinkedTreeMap
import com.nelipa.homeassigment.applicaster.utils.ApiConsts
import io.reactivex.Single
import retrofit2.http.GET

//those crazy return types because response JSON is malformed. Will be parsed later on
interface ApplicasterApi {
    @GET(ApiConsts.GET_LINKS)
    fun getLinks(): Single<LinkedTreeMap<String, Any>>

    @GET(ApiConsts.GET_VIDEOS)
    fun getVideos(): Single<LinkedTreeMap<String, Any>>
}