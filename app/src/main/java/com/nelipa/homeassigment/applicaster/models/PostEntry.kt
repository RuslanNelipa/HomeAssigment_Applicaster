package com.nelipa.homeassigment.applicaster.models

import androidx.annotation.Keep
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.nelipa.homeassigment.applicaster.utils.DatabaseConsts

@Keep
@Entity(tableName = DatabaseConsts.TableName.POSTS)
data class PostEntry(
    @PrimaryKey @ColumnInfo(name = DatabaseConsts.ColumnName.ID) val id: String,
    @ColumnInfo(name = DatabaseConsts.ColumnName.TITLE) val title: String? = null,
    @ColumnInfo(name = DatabaseConsts.ColumnName.SUMMARY) val summary: String? = null,
    @ColumnInfo(name = DatabaseConsts.ColumnName.PUBLISHED) val published: String? = null,
    @ColumnInfo(name = DatabaseConsts.ColumnName.CONTENT) val content: String? = null,
    @ColumnInfo(name = DatabaseConsts.ColumnName.TYPE) val type: PostType,
    @ColumnInfo(name = DatabaseConsts.ColumnName.LINK) val link: String? = null,
    @ColumnInfo(name = DatabaseConsts.ColumnName.MEDIA_GROUP) val mediaGroup: MediaGroup? = null
) {
    /**
     * Takes LARGE image if available. If not tries to get SMALL one
     * */
    fun getPreviewUrl() = getImageUrl(MediaScale.LARGE) ?: getImageUrl(MediaScale.SMALL) ?: getImageUrl(MediaScale.UNKNOWN)

    private fun getImageUrl(scale: MediaScale) = mediaGroup
        ?.mediaItems
        ?.filter { it.src.isNullOrEmpty().not() }
        ?.firstOrNull { it.scale == scale }
        ?.takeIf { it.type == MediaType.IMAGE }
        ?.src
}

data class MediaGroup(
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

