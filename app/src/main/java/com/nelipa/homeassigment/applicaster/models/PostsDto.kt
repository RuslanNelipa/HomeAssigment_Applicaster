package com.nelipa.homeassigment.applicaster.models

import androidx.annotation.Keep
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.nelipa.homeassigment.applicaster.utils.DatabaseConsts

@Keep
@Entity(tableName = DatabaseConsts.TableName.POSTS_LINKS)
data class PostLinkEntry(
    @PrimaryKey @ColumnInfo(name = DatabaseConsts.ColumnName.ID) val id: String,
    @ColumnInfo(name = DatabaseConsts.ColumnName.TITLE) val title: String,
    @ColumnInfo(name = DatabaseConsts.ColumnName.SUMMARY) val summary: String,
    @ColumnInfo(name = DatabaseConsts.ColumnName.PUBLISHED) val published: String,
    @ColumnInfo(name = DatabaseConsts.ColumnName.CONTENT) val content: String,
    @ColumnInfo(name = DatabaseConsts.ColumnName.TYPE) val type: String,
    @ColumnInfo(name = DatabaseConsts.ColumnName.LINK) val link: String,
    @ColumnInfo(name = DatabaseConsts.ColumnName.MEDIA_GROUP) @SerializedName("media_group") val mediaGroup: String
)

data class MediaGroup(
    @SerializedName("media_item") val mediaItems: List<MediaItem>
) : Iterable<MediaItem> {
    override fun iterator(): Iterator<MediaItem> = MediaItemIterator(mediaItems)

    class MediaItemIterator(
        private val items: List<MediaItem> = mutableListOf(),
        private var current: Int = 0
    ) : Iterator<MediaItem> {
        override fun hasNext() = items.size > current
        override fun next() = items[current].also { current++ }
    }
}

data class MediaItem(
    val src: String,
    val type: String,
    val scale: String
)

inline class Type(@SerializedName("value") val value: String)
inline class Link(@SerializedName("href") val value: String)
inline class Content(@SerializedName("content") val value: String)


