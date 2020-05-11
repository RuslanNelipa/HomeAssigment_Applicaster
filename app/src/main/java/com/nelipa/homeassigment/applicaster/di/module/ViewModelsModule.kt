package com.nelipa.homeassigment.applicaster.di.module

import androidx.lifecycle.ViewModel
import com.nelipa.homeassigment.applicaster.ui.posts.PostsViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module(includes = [ViewModelBuilderModule::class])
abstract class ViewModelsModule {

    @Binds
    @IntoMap
    @ViewModelKey(PostsViewModel::class)
    abstract fun bindPostsViewModel(vm: PostsViewModel): ViewModel
}