package com.powilliam.studyingcompose.stories.ui

import androidx.lifecycle.ViewModel
import com.powilliam.studyingcompose.stories.data.StoriesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class StoriesViewModel @Inject constructor(
    private val stories: StoriesRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(StoriesUiState())
    val uiState: StateFlow<StoriesUiState> = _uiState
}