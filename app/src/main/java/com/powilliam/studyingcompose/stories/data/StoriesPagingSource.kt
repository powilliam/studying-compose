package com.powilliam.studyingcompose.stories.data

import androidx.paging.PagingSource
import androidx.paging.PagingState

class StoriesPagingSource(
    private val dataSource: suspend (Int) -> DataTransferObject?
) : PagingSource<Int, Story>() {
    override fun getRefreshKey(state: PagingState<Int, Story>) = state.anchorPosition?.let { position ->
        val page = state.closestPageToPosition(position)
        page?.prevKey?.plus(1) ?: page?.nextKey?.minus(1)
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Story> {
        return try {
            val page = params.key ?: 1
            val data = dataSource(page)

            val prevKey = if (page == 1) null else page
            val nextKey = if (data?.page == data?.pages) null else (data?.page ?: 0) + 1

            LoadResult.Page(
                data = data?.stories ?: emptyList(),
                prevKey = prevKey,
                nextKey = nextKey
            )
        } catch (exception: Exception) {
            LoadResult.Error(exception)
        }
    }
}