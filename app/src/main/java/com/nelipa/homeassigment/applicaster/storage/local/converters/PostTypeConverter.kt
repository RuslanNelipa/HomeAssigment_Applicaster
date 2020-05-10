package com.nelipa.homeassigment.applicaster.storage.local.converters

import androidx.room.TypeConverter
import com.nelipa.homeassigment.applicaster.models.PostType

class PostTypeConverter {
    @TypeConverter
    fun fromPostType(target: PostType?): String? = target?.name

    @TypeConverter
    fun toPostType(target: String?): PostType? = target?.let { PostType.valueOf(it) }
}