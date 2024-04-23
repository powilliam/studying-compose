package com.powilliam.studyingcompose.stories.di

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.powilliam.studyingcompose.stories.data.StoriesDataAccessObject
import com.powilliam.studyingcompose.stories.data.StoriesRemoteDataSource
import com.powilliam.studyingcompose.stories.data.StoriesRemoteMediator
import com.powilliam.studyingcompose.stories.data.Story
import com.powilliam.studyingcompose.stories.data.StoryPagingKeyDataAccessObject
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier
import javax.inject.Singleton

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class LatestStoriesPager

@Module
@InstallIn(SingletonComponent::class)
object PagingModule {
    @OptIn(ExperimentalPagingApi::class)
    @LatestStoriesPager
    @Singleton
    @Provides
    fun provideLatestStoriesPager(
        stories: StoriesDataAccessObject,
        pagingKey: StoryPagingKeyDataAccessObject,
        dataSource: StoriesRemoteDataSource,
    ): Pager<Int, Story> = Pager(
        config = PagingConfig(pageSize = 20),
        remoteMediator = StoriesRemoteMediator(stories, pagingKey) { page ->
            dataSource.latest(tags = "story", page = page)
        }
    ) {
        stories.pagingSource()
    }
}