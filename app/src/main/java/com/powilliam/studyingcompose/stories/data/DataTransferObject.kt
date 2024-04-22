package com.powilliam.studyingcompose.stories.data

import com.google.gson.annotations.SerializedName

data class DataTransferObject(
    @SerializedName("hits")
    val stories: List<Story>,
    @SerializedName("hitsPerPage")
    val perPage: Int,
    @SerializedName("nbPages")
    val pages: Int,
    val page: Int,
)
