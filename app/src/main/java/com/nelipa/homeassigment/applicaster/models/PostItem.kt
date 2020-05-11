package com.nelipa.homeassigment.applicaster.models

import com.nelipa.homeassigment.applicaster.ext.Id

sealed class PostItem {
    class PostLinkItem(
        val postEntry: PostEntry,
        val linkItemClicked: (PostEntry) -> Unit
    ) : PostItem(), Id {
        override val id = postEntry.id
    }

    class PostVideoItem(
        val postEntry: PostEntry,
        val videoItemClicked: (PostEntry) -> Unit
    ) : PostItem(), Id {
        override val id = postEntry.id
    }

    object PostUnsupportedItem : PostItem(), Id {
        override val id = PostUnsupportedItem::class.java.name
    }
}