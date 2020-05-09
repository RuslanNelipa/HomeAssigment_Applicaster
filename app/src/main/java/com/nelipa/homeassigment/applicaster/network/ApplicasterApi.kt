package com.nelipa.homeassigment.applicaster.network

import com.nelipa.homeassigment.applicaster.models.PostLinkEntry
import com.nelipa.homeassigment.applicaster.utils.ApiConsts
import io.reactivex.Single
import retrofit2.http.POST

interface ApplicasterApi {
    @POST(ApiConsts.GET_LINKS)
    fun getLinks(): Single<PostLinkEntry>
}