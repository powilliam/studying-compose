package com.powilliam.studyingcompose.stories.data

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface TopicsDataAccessObject {
    @Query("SELECT * FROM topics, stories WHERE name = :name ORDER BY stories.updatedAt DESC")
    fun pagingSource(name: String): PagingSource<Int, TopicWithStories>

    @Query("SELECT * FROM topics WHERE name = :name")
    suspend fun topicWithStories(name: String): TopicWithStories?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun upsert(topic: Topic, topicStoryCrossRefs: List<TopicStoryCrossRef>)

    @Query("DELETE FROM topics WHERE name = :name")
    suspend fun nuke(name: String)
}