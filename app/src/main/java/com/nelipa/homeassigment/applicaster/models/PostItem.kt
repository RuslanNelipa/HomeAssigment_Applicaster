package com.nelipa.homeassigment.applicaster.models

import com.nelipa.homeassigment.applicaster.ext.Id

sealed class PostItem {
    class PostLinkItem(
        val postEntry: PostEntry,
        val linkItemClicked: () -> Unit
    ) : PostItem(), Id {
        override val id = postEntry.id
    }

    class PostVideoItem(
        val postEntry: PostEntry,
        val videoItemClicked: () -> Unit
    ) : PostItem(), Id {
        override val id = postEntry.id
    }
}