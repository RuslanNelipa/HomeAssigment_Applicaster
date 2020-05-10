package com.nelipa.homeassigment.applicaster.di.module

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.nelipa.homeassigment.applicaster.storage.remote.ApplicasterApi
import com.nelipa.homeassigment.applicaster.utils.ApiConsts
import dagger.Module
import dagger.Provides
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
object NetworkModule {
    @JvmStatic
    @Provides
    fun provideGson(): Gson = GsonBuilder()
        .setLenient()
        .create()

    @JvmStatic
    @Provides
    fun provideApplicasterApi(retrofit: Retrofit): ApplicasterApi =
        retrofit.create(ApplicasterApi::class.java)

    @JvmStatic
    @Singleton
    @Provides
    fun provideApplicasterRetrofit(
        okHttpClient: OkHttpClient,
        rxAdapter: RxJava2CallAdapterFactory,
        gson: Gson
    ): Retrofit = Retrofit.Builder()
        .baseUrl(ApiConsts.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .client(okHttpClient)
        .addCallAdapterFactory(rxAdapter)
        .build()

    @JvmStatic
    @Provides
    fun provideOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .build();

    @JvmStatic
    @Provides
    fun provideLoggingInterceptor(): HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    @JvmStatic
    @Provides
    fun provideRxAdapter(): RxJava2CallAdapterFactory =
        RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io())
}