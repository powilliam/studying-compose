package com.powilliam.studyingcompose.stories.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface StoryPagingKeyDataAccessObject {
    @Query("SELECT * FROM story_paging_keys WHERE topic = :topic ORDER BY createdAt DESC LIMIT 1")
    suspend fun latest(topic: String): StoryPagingKey?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(key: StoryPagingKey)

    @Query("DELETE FROM story_paging_keys WHERE topic = :topic")
    suspend fun nuke(topic: String)
}