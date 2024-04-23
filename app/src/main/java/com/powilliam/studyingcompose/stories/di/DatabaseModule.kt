package com.powilliam.studyingcompose.stories.di

import com.powilliam.studyingcompose.database.AppDatabase
import com.powilliam.studyingcompose.stories.data.StoriesDataAccessObject
import com.powilliam.studyingcompose.stories.data.StoryPagingKeyDataAccessObject
import com.powilliam.studyingcompose.stories.data.TopicsDataAccessObject
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Singleton
    @Provides
    fun provideStoriesDataAccessObject(database: AppDatabase): StoriesDataAccessObject =
        database.stories()

    @Singleton
    @Provides
    fun provideStoryPagingKeyDataAccessObject(database: AppDatabase): StoryPagingKeyDataAccessObject =
        database.storyPagingKeys()

    @Singleton
    @Provides
    fun provideTopicsDataAccessObject(database: AppDatabase): TopicsDataAccessObject =
        database.topics()
}