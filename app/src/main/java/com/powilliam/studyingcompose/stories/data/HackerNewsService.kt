package com.powilliam.studyingcompose.stories.data

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface HackerNewsService {
    @GET("{version}/search_by_date")
    suspend fun latest(
        @Path("version") version: String = "v1",
        @Query("page") page: Int = 1,
        @Query("hitsPerPage") perPage: Int = 20,
        @Query("tags") tags: String,
    ): Response<DataTransferObject>
}