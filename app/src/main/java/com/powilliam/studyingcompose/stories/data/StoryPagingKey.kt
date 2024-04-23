package com.powilliam.studyingcompose.stories.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "story_paging_keys")
data class StoryPagingKey(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val topic: String,
    val prevKey: Int? = null,
    val nextKey: Int? = null,
    val createdAt: Long = System.currentTimeMillis()
)
