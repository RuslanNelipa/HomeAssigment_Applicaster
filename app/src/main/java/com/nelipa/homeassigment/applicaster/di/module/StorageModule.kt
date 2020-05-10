package com.nelipa.homeassigment.applicaster.di.module

import android.content.Context
import androidx.room.Room
import com.nelipa.homeassigment.applicaster.storage.local.ApplicasterDatabase
import com.nelipa.homeassigment.applicaster.utils.DatabaseConsts
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object StorageModule {

    @JvmStatic
    @Singleton
    @Provides
    fun provideDataBase(context: Context): ApplicasterDatabase = Room.databaseBuilder(
        context.applicationContext,
        ApplicasterDatabase::class.java,
        DatabaseConsts.DATABASE_NAME
    ).build()

    @JvmStatic
    @Singleton
    @Provides
    fun providePostsDao(database: ApplicasterDatabase) = database.postsDao()
}