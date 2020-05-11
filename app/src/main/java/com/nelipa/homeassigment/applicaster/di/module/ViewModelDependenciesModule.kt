package com.nelipa.homeassigment.applicaster.di.module

import com.nelipa.homeassigment.applicaster.di.scope.ActivityScope
import com.nelipa.homeassigment.applicaster.managers.contract.NetworkManager
import com.nelipa.homeassigment.applicaster.managers.implementation.NetworkManagerImpl
import com.nelipa.homeassigment.applicaster.managers.contract.PostsParser
import com.nelipa.homeassigment.applicaster.managers.contract.SearchQueryProvider
import com.nelipa.homeassigment.applicaster.managers.implementation.PostsParserImpl
import com.nelipa.homeassigment.applicaster.managers.implementation.SearchQueryManagerImpl
import com.nelipa.homeassigment.applicaster.storage.PostsRepository
import com.nelipa.homeassigment.applicaster.storage.PostsRepositoryImpl
import com.nelipa.homeassigment.applicaster.storage.local.PostsDao
import com.nelipa.homeassigment.applicaster.storage.remote.ApplicasterApi
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module(includes = [ManagersModule::class])
object ViewModelDependenciesModule {
    @JvmStatic
    @Provides
    @ActivityScope
    fun provideNetworkManager(api: ApplicasterApi, parser: PostsParser): NetworkManager {
        return NetworkManagerImpl(
            api,
            parser
        )
    }

    @JvmStatic
    @Provides
    @ActivityScope
    fun providePostsRepository(networkManager: NetworkManager, postsDao: PostsDao): PostsRepository {
        return PostsRepositoryImpl(networkManager, postsDao)
    }
}

@Module
abstract class ManagersModule {
    @Binds
    @ActivityScope
    internal abstract fun bindPostsParser(impl: PostsParserImpl): PostsParser

    @Binds
    @ActivityScope
    internal abstract fun bindSearchQueryProvider(impl: SearchQueryManagerImpl): SearchQueryProvider
}