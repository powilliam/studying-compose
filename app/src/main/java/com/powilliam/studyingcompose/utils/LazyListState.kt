package com.powilliam.studyingcompose.utils

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.produceState
import androidx.compose.runtime.snapshotFlow
import kotlinx.coroutines.flow.collectLatest

@Composable
fun LazyListState.collectCanScrollToTopAsState(threshold: Int = 10): State<Boolean> {
    return produceState(initialValue = false) {
        snapshotFlow { firstVisibleItemIndex }
            .collectLatest { index ->
                value = index > threshold
            }
    }
}