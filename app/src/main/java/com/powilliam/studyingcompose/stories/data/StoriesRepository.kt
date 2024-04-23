package com.powilliam.studyingcompose.stories.data

import androidx.paging.Pager
import androidx.paging.PagingData
import com.powilliam.studyingcompose.stories.di.LatestStoriesPager
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface StoriesRepository {
    val latestStories: Flow<PagingData<TopicWithStories>>
}

class StoriesRepositoryImpl @Inject constructor(
    @LatestStoriesPager
    private val pager: Pager<Int, TopicWithStories>
) : StoriesRepository {
    override val latestStories = pager.flow
}