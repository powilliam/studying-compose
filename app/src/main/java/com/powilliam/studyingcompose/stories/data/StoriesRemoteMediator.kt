package com.powilliam.studyingcompose.stories.data

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.time.Duration
import java.time.Instant

@OptIn(ExperimentalPagingApi::class)
class StoriesRemoteMediator(
    private val stories: StoriesDataAccessObject,
    private val pagingKeys: StoryPagingKeyDataAccessObject,
    private val dataSource: suspend (Int) -> DataTransferObject?
) : RemoteMediator<Int, Story>() {
    override suspend fun initialize(): InitializeAction {
        val latestPagingKey = withContext(Dispatchers.IO) {
            pagingKeys.latest()
        } ?: return InitializeAction.LAUNCH_INITIAL_REFRESH

        val maxAge = Instant.ofEpochMilli(latestPagingKey.createdAt)
            .plus(Duration.ofHours(6))

        if (Instant.now().isAfter(maxAge)) {
            Log.d("StoriesRemoteMediator:initialize", "max age reached")
            return InitializeAction.LAUNCH_INITIAL_REFRESH
        }

        Log.d("StoriesRemoteMediator:initialize", "max age not reached")

        return InitializeAction.SKIP_INITIAL_REFRESH
    }

    override suspend fun load(loadType: LoadType, state: PagingState<Int, Story>): MediatorResult {
        return try {
            Log.d("StoriesRemoteMediator:load", "$loadType")

            val page = when(loadType) {
                LoadType.REFRESH -> null
                LoadType.PREPEND -> {
                    return MediatorResult.Success(endOfPaginationReached = true)
                }
                LoadType.APPEND -> {
                    val latestPagingKey = withContext(Dispatchers.IO) {
                        pagingKeys.latest()
                    }

                    latestPagingKey?.nextKey
                }
            }

            val data = dataSource(page ?: 1)

            Log.d("StoriesRemoteMediator:load", "page: ${data?.page}, pages: ${data?.pages}")

            withContext(Dispatchers.IO) {
                if (loadType == LoadType.REFRESH) {
                    pagingKeys.nuke()
                    stories.nuke()
                }

                pagingKeys.upsert(StoryPagingKey(prevKey = page ?: 1, nextKey = (data?.page ?: 1) + 1))
                stories.upsert(data?.stories ?: emptyList())
            }

            Log.d("StoriesRemoteMediator:load", "${data?.page == data?.pages}")

            MediatorResult.Success(endOfPaginationReached = data?.stories?.isEmpty() ?: false)
        } catch (exception: Exception) {
            Log.d("StoriesRemoteMediator:load:catch", "$exception")

            MediatorResult.Error(exception)
        }
    }
}