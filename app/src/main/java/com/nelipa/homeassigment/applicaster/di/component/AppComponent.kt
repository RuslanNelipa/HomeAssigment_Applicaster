package com.nelipa.homeassigment.applicaster.di.component

import android.content.Context
import com.nelipa.homeassigment.applicaster.App
import com.nelipa.homeassigment.applicaster.di.module.ApplicationModule
import com.nelipa.homeassigment.applicaster.di.scope.AppScope
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@AppScope
@Singleton
@Component(
    modules = [
        ApplicationModule::class,
        AndroidSupportInjectionModule::class
    ]
)
interface AppComponent: AndroidInjector<App> {
    @Component.Factory
    interface Factory {
        fun create(@BindsInstance applicationContext: Context): AppComponent
    }
}