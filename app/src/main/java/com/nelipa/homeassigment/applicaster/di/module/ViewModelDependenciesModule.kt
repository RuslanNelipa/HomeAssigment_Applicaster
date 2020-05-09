package com.nelipa.homeassigment.applicaster.di.module

import com.nelipa.homeassigment.applicaster.storage.PostsRepository
import com.nelipa.homeassigment.applicaster.storage.PostsRepositoryImpl
import dagger.Binds
import dagger.Module

@Module
abstract class ViewModelDependenciesModule {
    @Binds
    internal abstract fun bindRepository(repository: PostsRepositoryImpl): PostsRepository
}