package com.powilliam.studyingcompose.stories.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface StoryPagingKeyDataAccessObject {
    @Query("SELECT * FROM story_paging_keys ORDER BY createdAt DESC LIMIT 1")
    suspend fun latest(): StoryPagingKey?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(key: StoryPagingKey)

    @Query("DELETE FROM story_paging_keys")
    suspend fun nuke()
}