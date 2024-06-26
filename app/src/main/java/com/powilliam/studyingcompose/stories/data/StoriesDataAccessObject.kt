package com.powilliam.studyingcompose.stories.data

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface StoriesDataAccessObject {
    @Query("SELECT * FROM stories ORDER BY updatedAt DESC")
    fun pagingSource(): PagingSource<Int, Story>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(stories: List<Story>)

    @Delete
    suspend fun nukeTheseNuts(stories: List<Story>)

    @Query("DELETE FROM stories")
    suspend fun nuke()
}