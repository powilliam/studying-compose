package com.powilliam.studyingcompose.stories.di

import com.powilliam.studyingcompose.stories.data.StoriesRepository
import com.powilliam.studyingcompose.stories.data.StoriesRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Singleton
    @Binds
    abstract fun bindStoriesRepository(impl: StoriesRepositoryImpl): StoriesRepository
}