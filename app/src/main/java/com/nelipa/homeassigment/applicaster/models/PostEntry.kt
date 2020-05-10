package com.nelipa.homeassigment.applicaster.models

import androidx.annotation.Keep
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.nelipa.homeassigment.applicaster.utils.DatabaseConsts

@Keep
@Entity(tableName = DatabaseConsts.TableName.POSTS_LINKS)
data class PostEntry(
    @PrimaryKey @ColumnInfo(name = DatabaseConsts.ColumnName.ID) val id: String,
    @ColumnInfo(name = DatabaseConsts.ColumnName.TITLE) val title: String? = null,
    @ColumnInfo(name = DatabaseConsts.ColumnName.SUMMARY) val summary: String? = null,
    @ColumnInfo(name = DatabaseConsts.ColumnName.PUBLISHED) val published: String? = null,
    @ColumnInfo(name = DatabaseConsts.ColumnName.CONTENT) val content: String? = null,
    @ColumnInfo(name = DatabaseConsts.ColumnName.TYPE) val type: PostType? = null,
    @ColumnInfo(name = DatabaseConsts.ColumnName.LINK) val link: String? = null,
    @ColumnInfo(name = DatabaseConsts.ColumnName.MEDIA_GROUP) val mediaGroup: MediaGroup? = null
)

data class MediaGroup (
    @ColumnInfo(name = DatabaseConsts.ColumnName.MEDIA_ITEMS) val mediaItems: List<MediaItem>? = null
)

data class MediaItem(
    @ColumnInfo(name = DatabaseConsts.ColumnName.MEDIA_TYPE) val type: MediaType,
    @ColumnInfo(name = DatabaseConsts.ColumnName.SCALE) val scale: MediaScale,
    @ColumnInfo(name = DatabaseConsts.ColumnName.SRC) val src: String? = null
)

enum class PostType {
    VIDEO, LINK, UNKNOWN
}

enum class MediaType {
    IMAGE, UNKNOWN
}

enum class MediaScale {
    SMALL, LARGE, UNKNOWN
}

