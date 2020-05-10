package com.nelipa.homeassigment.applicaster.storage.local.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.nelipa.homeassigment.applicaster.models.MediaGroup

//todo: Don't use objects converters in production! Add additional table with relation instead!
class MediaGroupConverter {
    @TypeConverter
    fun fromMediaGroupType(target: MediaGroup?): String? = Gson().toJson(target)

    @TypeConverter
    fun toMediaGroupType(target: String?): MediaGroup? = target?.let {
        Gson().fromJson(it, MediaGroup::class.java)
    }
}