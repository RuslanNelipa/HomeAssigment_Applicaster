package com.nelipa.homeassigment.applicaster.storage.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.nelipa.homeassigment.applicaster.models.PostEntry
import com.nelipa.homeassigment.applicaster.storage.local.converters.MediaGroupConverter
import com.nelipa.homeassigment.applicaster.storage.local.converters.MediaScaleConverter
import com.nelipa.homeassigment.applicaster.storage.local.converters.MediaTypeConverter
import com.nelipa.homeassigment.applicaster.storage.local.converters.PostTypeConverter

@Database(entities = [PostEntry::class], version = 1, exportSchema = false)
@TypeConverters(
    PostTypeConverter::class,
    MediaTypeConverter::class,
    MediaScaleConverter::class,
    MediaGroupConverter::class
)
abstract class ApplicasterDatabase : RoomDatabase() {

    abstract fun postsDao(): PostsDao
}