package com.nelipa.homeassigment.applicaster.storage.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.nelipa.homeassigment.applicaster.models.PostLinkEntry

@Database(entities = [PostLinkEntry::class], version = 1, exportSchema = false)
abstract class ApplicasterDatabase : RoomDatabase() {

    abstract fun postsDao(): PostsDao
}