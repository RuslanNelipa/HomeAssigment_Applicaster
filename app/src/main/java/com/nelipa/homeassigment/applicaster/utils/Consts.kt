package com.nelipa.homeassigment.applicaster.utils

object ApiConsts {
    const val BASE_URL = "http://assets-production.applicaster.com/applicaster-employees/"
    const val GET_LINKS = "applicaster-employees/israel_team/Elad/assignment/link_json.json"
    const val GET_VIDEO = "applicaster-employees/israel_team/Elad/assignment/video_json.json"
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
    }
}

