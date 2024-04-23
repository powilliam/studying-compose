package com.powilliam.studyingcompose.stories.data

import androidx.compose.ui.util.fastMap
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.time.Duration
import java.time.Instant

data class StoriesRemoteMediatorConfig(val topic: String, val cacheMaxAgeInHours: Long)

@OptIn(ExperimentalPagingApi::class)
class StoriesRemoteMediator(
    private val topics: TopicsDataAccessObject,
    private val stories: StoriesDataAccessObject,
    private val pagingKeys: StoryPagingKeyDataAccessObject,
    private val config: StoriesRemoteMediatorConfig,
    private val dataSource: suspend (Int) -> DataTransferObject?
) : RemoteMediator<Int, TopicWithStories>() {
    override suspend fun initialize(): InitializeAction {
        val latestPagingKey = withContext(Dispatchers.IO) {
            pagingKeys.latest(config.topic)
        } ?: return InitializeAction.LAUNCH_INITIAL_REFRESH

        val maxAge = Instant.ofEpochMilli(latestPagingKey.createdAt)
            .plus(Duration.ofHours(config.cacheMaxAgeInHours))

        if (Instant.now().isAfter(maxAge)) {
            return InitializeAction.LAUNCH_INITIAL_REFRESH
        }

        return InitializeAction.SKIP_INITIAL_REFRESH
    }

    override suspend fun load(loadType: LoadType, state: PagingState<Int, TopicWithStories>): MediatorResult {
        return try {
            val page = when(loadType) {
                LoadType.REFRESH -> null
                LoadType.PREPEND -> {
                    return MediatorResult.Success(endOfPaginationReached = true)
                }
                LoadType.APPEND -> {
                    val latestPagingKey = withContext(Dispatchers.IO) {
                        pagingKeys.latest(config.topic)
                    }

                    latestPagingKey?.nextKey
                }
            }

            val data = dataSource(page ?: 1)

            withContext(Dispatchers.IO) {
                if (loadType == LoadType.REFRESH) {
                    topics.topicWithStories(config.topic)?.let { topicWithStories ->
                        stories.nukeTheseNuts(topicWithStories.stories)
                        topics.nuke(topicWithStories.topic.name)
                        pagingKeys.nuke(config.topic)
                    }
                }

                pagingKeys.upsert(
                    StoryPagingKey(
                        prevKey = page ?: 1,
                        nextKey = (data?.page ?: 1) + 1,
                        topic = config.topic,
                    )
                )

                val everything = (data?.stories ?: emptyList())
                val crossRefs = everything.fastMap { story ->
                    TopicStoryCrossRef(topicName = config.topic, storyId = story.id)
                }

                stories.upsert(everything)
                topics.upsert(topic = Topic(config.topic), topicStoryCrossRefs = crossRefs)
            }

            MediatorResult.Success(endOfPaginationReached = data?.stories?.isEmpty() ?: false)
        } catch (exception: Exception) {
            MediatorResult.Error(exception)
        }
    }
}