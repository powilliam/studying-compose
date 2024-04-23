package com.powilliam.studyingcompose.di

import android.content.Context
import androidx.room.Room
import com.powilliam.studyingcompose.database.AppDatabase
import com.powilliam.studyingcompose.stories.data.StoriesDataAccessObject
import com.powilliam.studyingcompose.stories.data.StoryPagingKeyDataAccessObject
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase =
        Room.databaseBuilder(context, AppDatabase::class.java, "com.powilliam.studyingcompose")
            .build()

    @Singleton
    @Provides
    fun provideStoriesDataAccessObject(database: AppDatabase): StoriesDataAccessObject =
        database.stories()

    @Singleton
    @Provides
    fun provideStoryPagingKeyDataAccessObject(database: AppDatabase): StoryPagingKeyDataAccessObject =
        database.storyPagingKeys()
}