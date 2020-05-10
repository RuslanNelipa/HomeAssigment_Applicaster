package com.nelipa.homeassigment.applicaster.di.module

import com.nelipa.homeassigment.applicaster.di.scope.ActivityScope
import com.nelipa.homeassigment.applicaster.di.scope.FragmentScope
import com.nelipa.homeassigment.applicaster.managers.NetworkManager
import com.nelipa.homeassigment.applicaster.managers.NetworkManagerImpl
import com.nelipa.homeassigment.applicaster.managers.PostsParser
import com.nelipa.homeassigment.applicaster.managers.PostsParserImpl
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
    @ActivityScope
    @Provides
    fun provideNetworkManager(api: ApplicasterApi, parser: PostsParser): NetworkManager {
        return NetworkManagerImpl(api, parser)
    }

    @JvmStatic
    @ActivityScope
    @Provides
    fun providePostsRepository(networkManager: NetworkManager, postsDao: PostsDao): PostsRepository {
        return PostsRepositoryImpl(networkManager, postsDao)
    }
}

@Module
abstract class ManagersModule {
    @Binds internal abstract fun bindPostsParser(impl: PostsParserImpl): PostsParser
}