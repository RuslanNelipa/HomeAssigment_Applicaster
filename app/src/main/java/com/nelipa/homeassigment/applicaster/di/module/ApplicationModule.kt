package com.nelipa.homeassigment.applicaster.di.module

import dagger.Module

@Module(
        includes = [
            ActivityModule::class,
            NetworkModule::class,
            StorageModule::class
        ]
)
object ApplicationModule
