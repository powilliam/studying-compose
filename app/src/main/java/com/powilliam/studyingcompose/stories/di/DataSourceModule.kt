package com.powilliam.studyingcompose.stories.di

import com.powilliam.studyingcompose.stories.data.StoriesRemoteDataSource
import com.powilliam.studyingcompose.stories.data.StoriesRemoteDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {
    @Singleton
    @Binds
    abstract fun bindStoriesRemoteDataSource(impl: StoriesRemoteDataSourceImpl): StoriesRemoteDataSource
}