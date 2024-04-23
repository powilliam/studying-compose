package com.powilliam.studyingcompose.stories.data

import androidx.compose.runtime.Stable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Stable
@Entity(tableName = "stories")
data class Story(
    @PrimaryKey
    @SerializedName("story_id")
    val id: Long = 0,
    val title: String,
    val author: String,
    val url: String?,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("updated_at")
    val updatedAt: String,
)