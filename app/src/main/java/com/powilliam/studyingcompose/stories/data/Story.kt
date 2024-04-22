package com.powilliam.studyingcompose.stories.data

import com.google.gson.annotations.SerializedName

data class Story(
    val title: String,
    val author: String,
    val url: String,
    @SerializedName("story_id")
    val id: Long = 0,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("updated_at")
    val updatedAt: String,
)
