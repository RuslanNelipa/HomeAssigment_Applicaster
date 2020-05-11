package com.nelipa.homeassigment.applicaster.managers

import com.google.gson.internal.LinkedTreeMap
import com.nelipa.homeassigment.applicaster.ext.firstOrNull
import com.nelipa.homeassigment.applicaster.ext.forEach
import com.nelipa.homeassigment.applicaster.ext.getStringOrNull
import com.nelipa.homeassigment.applicaster.models.*
import com.nelipa.homeassigment.applicaster.utils.DeserializeConsts
import io.reactivex.Single
import org.json.JSONArray
import org.json.JSONObject
import java.util.*
import javax.inject.Inject

class PostsParserImpl @Inject constructor() : PostsParser {
    override fun parsePosts(raw: LinkedTreeMap<String, Any>): Single<List<PostEntry>> {
        return Single.create { _emitter ->
            val result = mutableListOf<PostEntry>()
            val entriesArray = JSONObject(raw).getJSONArray(DeserializeConsts.ENTRY)
            result.populateWithPosts(entriesArray)

            _emitter.onSuccess(result)
        }
    }

    private fun MutableList<PostEntry>.populateWithPosts(entriesArray: JSONArray) =
        entriesArray.forEach { _entry ->
            add(
                PostEntry(
                    id = _entry.getStringOrNull(DeserializeConsts.ID) ?: UUID.randomUUID()
                        .toString(),
                    type = _entry.parseType(),
                    title = _entry.getStringOrNull(DeserializeConsts.TITLE),
                    summary = _entry.getStringOrNull(DeserializeConsts.SUMMARY),
                    published = _entry.getStringOrNull(DeserializeConsts.PUBLISHED),
                    content = _entry.parseContent(),
                    link = _entry.parseLink(),
                    mediaGroup = _entry.parseMediaGroup()
                )
            )
        }

    private fun JSONObject.parseContent() = getJSONObject(DeserializeConsts.CONTENT)
        .getStringOrNull(DeserializeConsts.CONTENT)

    private fun JSONObject.parseLink() = getJSONObject(DeserializeConsts.LINK)
        .getStringOrNull(DeserializeConsts.HREF)

    private fun JSONObject.parseType() = getJSONObject(DeserializeConsts.TYPE)
        .getStringOrNull(DeserializeConsts.VALUE)
        ?.let {
            when (it) {
                DeserializeConsts.LINK -> PostType.LINK
                DeserializeConsts.VIDEO -> PostType.VIDEO
                else -> PostType.UNKNOWN
            }
        }
        ?: PostType.UNKNOWN

    private fun JSONObject.parseMediaGroup() = getJSONArray(DeserializeConsts.MEDIA_GROUP)
        .firstOrNull()
        ?.getJSONArray(DeserializeConsts.MEDIA_ITEM)
        ?.let { _array ->
            val mediaItems = mutableListOf<MediaItem>()
            _array.forEach { _mediaItem ->
                MediaItem(
                    type = _mediaItem.parseMediaType(),
                    scale = _mediaItem.parseMediaScale(),
                    src = _mediaItem.getStringOrNull(DeserializeConsts.SRC)
                ).let(mediaItems::add)
            }
            MediaGroup(mediaItems)
        }


    private fun JSONObject.parseMediaType() = when (getStringOrNull(DeserializeConsts.TYPE)) {
        DeserializeConsts.IMAGE -> MediaType.IMAGE
        else -> MediaType.UNKNOWN
    }

    private fun JSONObject.parseMediaScale() = when (getStringOrNull(DeserializeConsts.SCALE)) {
        DeserializeConsts.SCALE_SMALL -> MediaScale.SMALL
        DeserializeConsts.SCALE_LARGE -> MediaScale.LARGE
        else -> MediaScale.UNKNOWN
    }
}
