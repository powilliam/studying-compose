package com.powilliam.studyingcompose.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.powilliam.studyingcompose.stories.data.StoriesDataAccessObject
import com.powilliam.studyingcompose.stories.data.Story
import com.powilliam.studyingcompose.stories.data.StoryPagingKey
import com.powilliam.studyingcompose.stories.data.StoryPagingKeyDataAccessObject
import com.powilliam.studyingcompose.stories.data.Topic
import com.powilliam.studyingcompose.stories.data.TopicStoryCrossRef
import com.powilliam.studyingcompose.stories.data.TopicsDataAccessObject

@Database(
    entities = [Story::class, StoryPagingKey::class, Topic::class, TopicStoryCrossRef::class],
    version = 4
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun stories(): StoriesDataAccessObject

    abstract fun storyPagingKeys(): StoryPagingKeyDataAccessObject

    abstract fun topics(): TopicsDataAccessObject
}