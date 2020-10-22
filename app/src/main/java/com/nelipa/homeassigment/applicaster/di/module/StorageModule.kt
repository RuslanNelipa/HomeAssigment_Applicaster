package com.nelipa.homeassigment.applicaster.di.module

import android.content.Context
import androidx.room.Room
import com.nelipa.homeassigment.applicaster.storage.local.ApplicasterDatabase
import com.nelipa.homeassigment.applicaster.utils.DatabaseConsts
import dagger.Module
import dagger.Provides
import dagger.Reusable
import javax.inject.Singleton

@Module
interface StorageModule {
    companion object {
        @Provides
        @Reusable
        fun providePostsDao(database: ApplicasterDatabase) = database.postsDao()

        @Provides
        @Reusable
        fun provideDataBase(context: Context): ApplicasterDatabase = Room.databaseBuilder(
            context.applicationContext,
            ApplicasterDatabase::class.java,
            DatabaseConsts.DATABASE_NAME
        ).build()
    }
}