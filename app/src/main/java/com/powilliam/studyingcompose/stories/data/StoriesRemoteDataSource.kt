package com.powilliam.studyingcompose.stories.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface StoriesRemoteDataSource {
    suspend fun latest(tags: String, page: Int = 1, perPage: Int = 20): DataTransferObject?
}

class StoriesRemoteDataSourceImpl @Inject constructor(
    private val service: HackerNewsService
) : StoriesRemoteDataSource {
    override suspend fun latest(
        tags: String,
        page: Int,
        perPage: Int
    ) = withContext(Dispatchers.IO) {
        service.latest(tags = tags, page = page, perPage = perPage).body()
    }
}