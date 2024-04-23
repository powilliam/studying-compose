package com.powilliam.studyingcompose.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.powilliam.studyingcompose.stories.data.StoriesDataAccessObject
import com.powilliam.studyingcompose.stories.data.Story
import com.powilliam.studyingcompose.stories.data.StoryPagingKey
import com.powilliam.studyingcompose.stories.data.StoryPagingKeyDataAccessObject

@Database(entities = [Story::class, StoryPagingKey::class], version = 2)
abstract class AppDatabase : RoomDatabase() {
    abstract fun stories(): StoriesDataAccessObject

    abstract fun storyPagingKeys(): StoryPagingKeyDataAccessObject
}