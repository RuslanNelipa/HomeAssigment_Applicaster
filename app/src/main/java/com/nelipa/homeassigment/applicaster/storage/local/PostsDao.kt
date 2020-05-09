/*
 * Copyright (C) 2019 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.nelipa.homeassigment.applicaster.storage.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.nelipa.homeassigment.applicaster.models.PostLinkEntry
import com.nelipa.homeassigment.applicaster.utils.DatabaseConsts
import io.reactivex.Completable
import io.reactivex.Observable

/**
 * Data Access Object for the posts table.
 */
@Dao
interface PostsDao {

    /**
     * Select all post links from the links table.
     *
     * @return all links.
     */
    @Query("SELECT * FROM ${DatabaseConsts.TableName.POSTS_LINKS}")
    fun gePostLinkEntries(): Observable<List<PostLinkEntry>>

    /**
     * Insert a post link in the database. If the post already exists, replace it.
     *
     * @param post the link entry to be inserted.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPost(post: PostLinkEntry): Completable

    /**
     * Delete all post link entries.
     */
    @Query("DELETE FROM ${DatabaseConsts.TableName.POSTS_LINKS}")
    fun deletePostLink()

}
