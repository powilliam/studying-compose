package com.powilliam.studyingcompose.stories.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.powilliam.studyingcompose.stories.data.StoriesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class StoriesViewModel @Inject constructor(
    private val repository: StoriesRepository
) : ViewModel() {
    val paging = repository.latestStories.cachedIn(viewModelScope)
}