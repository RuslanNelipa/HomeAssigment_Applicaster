package com.nelipa.homeassigment.applicaster.utils

object ApiConsts {
    const val BASE_URL = "http://assets-production.applicaster.com/"
    const val GET_LINKS = "applicaster-employees/israel_team/Elad/assignment/link_json.json"
    const val GET_VIDEOS = "applicaster-employees/israel_team/Elad/assignment/video_json.json"
}

object DeserializeConsts {
    const val ENTRY = "entry"
    const val TITLE = "title"
    const val SUMMARY = "summary"
    const val PUBLISHED = "published"
    const val ID = "id"
    const val CONTENT = "content"
    const val TYPE = "type"
    const val SRC = "src"
    const val SCALE = "scale"
    const val LINK = "link"
    const val HREF = "href"
    const val VALUE = "value"
    const val VIDEO = "video"
    const val IMAGE = "image"
    const val SCALE_SMALL = "small"
    const val SCALE_LARGE = "large"
    const val MEDIA_GROUP = "media_group"
    const val MEDIA_ITEM = "media_item"
}

object DatabaseConsts {
    const val DATABASE_NAME = "applicaster.db"

    object TableName {
        const val POSTS_LINKS = "table_posts_links"
    }

    object ColumnName {
        const val TITLE = "title"
        const val SUMMARY = "summary"
        const val PUBLISHED = "published"
        const val ID = "id"
        const val CONTENT = "content"
        const val TYPE = "type"
        const val LINK = "link"
        const val MEDIA_GROUP = "media_group"
        const val MEDIA_ITEMS = "media_items"
        const val MEDIA_TYPE = "media_type"
        const val SCALE = "scale"
        const val SRC = "src"
    }
}

