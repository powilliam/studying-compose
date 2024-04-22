package com.powilliam.studyingcompose.stories.ui

import com.powilliam.studyingcompose.stories.data.Story

data class StoriesUiState(
    val isGettingStories: Boolean = false,
    val stories: List<Story> = emptyList()
)
