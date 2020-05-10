package com.nelipa.homeassigment.applicaster.storage.local.converters

import androidx.room.TypeConverter
import com.nelipa.homeassigment.applicaster.models.MediaScale

class MediaScaleConverter {
    @TypeConverter
    fun fromMediaScale(target: MediaScale?): String? = target?.name

    @TypeConverter
    fun toMediaScale(target: String?): MediaScale? = target?.let { MediaScale.valueOf(it) }
}
