package com.powilliam.studyingcompose.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems

@Composable
fun <T : Any> LazyPagingItems<T>.isLoading(
    callback: (CombinedLoadStates) -> LoadState
): State<Boolean> {
    return remember {
        derivedStateOf { callback(loadState) == LoadState.Loading }
    }
}