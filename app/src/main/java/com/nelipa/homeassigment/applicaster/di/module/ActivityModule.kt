package com.nelipa.homeassigment.applicaster.di.module

import com.nelipa.homeassigment.applicaster.di.scope.ActivityScope
import com.nelipa.homeassigment.applicaster.ui.navigation.NavigatorActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
interface ActivityModule {

    @[ContributesAndroidInjector(modules = [FragmentsBuilderModule::class]) ActivityScope]
    fun contributeNavigatorActivity(): NavigatorActivity
}