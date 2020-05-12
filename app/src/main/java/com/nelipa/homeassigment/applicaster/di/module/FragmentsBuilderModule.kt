package com.nelipa.homeassigment.applicaster.di.module

import com.nelipa.homeassigment.applicaster.di.scope.FragmentScope
import com.nelipa.homeassigment.applicaster.ui.link.LinkPostDetailsFragment
import com.nelipa.homeassigment.applicaster.ui.posts.PostsFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module(includes = [ViewModelsModule::class])
interface FragmentsBuilderModule {

    @[ContributesAndroidInjector FragmentScope]
    fun postsFragment(): PostsFragment

    @[ContributesAndroidInjector FragmentScope]
    fun linkPostDetailsFragment(): LinkPostDetailsFragment
}