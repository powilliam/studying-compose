package com.powilliam.studyingcompose.stories.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface StoriesRepository {
    val latestStories: Flow<PagingData<Story>>
}

class StoriesRepositoryImpl @Inject constructor(
    private val dataSource: StoriesRemoteDataSource
) : StoriesRepository {
    override val latestStories = Pager(PagingConfig(pageSize = 20)) {
        StoriesPagingSource { dataSource.latest(tags = "story", page = it) }
    }.flow
}