package com.powilliam.studyingcompose.stories.data

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Junction
import androidx.room.Relation

@Entity(primaryKeys = ["name", "id"])
data class TopicStoryCrossRef(
    @ColumnInfo(name = "name") val topicName: String,
    @ColumnInfo(name = "id") val storyId: Long
)

data class TopicWithStories(
    @Embedded val topic: Topic,
    @Relation(
        parentColumn = "name",
        entityColumn =  "id",
        associateBy = Junction(TopicStoryCrossRef::class)
    )
    val stories: List<Story>
)
