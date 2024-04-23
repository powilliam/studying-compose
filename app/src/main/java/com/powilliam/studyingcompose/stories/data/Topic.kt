package com.powilliam.studyingcompose.stories.data

import androidx.compose.runtime.Stable
import androidx.room.Entity
import androidx.room.PrimaryKey

@Stable
@Entity(tableName = "topics")
data class Topic(@PrimaryKey val name: String)
