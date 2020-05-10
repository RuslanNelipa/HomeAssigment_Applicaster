package com.nelipa.homeassigment.applicaster.storage.local.converters

import androidx.room.TypeConverter
import com.nelipa.homeassigment.applicaster.models.MediaType

class MediaTypeConverter {
    @TypeConverter
    fun fromMediaType(target: MediaType?): String? = target?.name

    @TypeConverter
    fun toMediaType(target: String?): MediaType? = target?.let { MediaType.valueOf(it) }
}
