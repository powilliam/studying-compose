package com.powilliam.studyingcompose.utils

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import androidx.paging.CombinedLoadStates
import androidx.paging.DifferCallback
import androidx.paging.ItemSnapshotList
import androidx.paging.LoadState
import androidx.paging.LoadStates
import androidx.paging.NullPaddedList
import androidx.paging.PagingData
import androidx.paging.PagingDataDiffer
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.filterNotNull

typealias DifferCallbackEvent = (Int, Int) -> Unit

@SuppressLint("RestrictedApi")
@Composable
fun rememberDifferCallback(
    onRemoved: DifferCallbackEvent = { position, count -> },
    onInserted: DifferCallbackEvent = { position, count -> },
    onChanged: DifferCallbackEvent = { position, count -> },
): DifferCallback {
    val currentOnRemoved by rememberUpdatedState(onRemoved)
    val currentOnInserted by rememberUpdatedState(onInserted)
    val currentOnChanged by rememberUpdatedState(onChanged)

    return remember {
        object : DifferCallback {
            override fun onRemoved(position: Int, count: Int) = currentOnRemoved(position, count)

            override fun onInserted(position: Int, count: Int) = currentOnInserted(position, count)

            override fun onChanged(position: Int, count: Int) = currentOnChanged(position, count)
        }
    }
}

@SuppressLint("RestrictedApi")
@Composable
fun <T : Any> rememberPagingDataDiffer(
    callback: DifferCallback,
    onPresentNewList: (PagingDataDiffer<T>, () -> Unit) -> Int?
): PagingDataDiffer<T> {
    val currentOnPresentNewList by rememberUpdatedState(onPresentNewList)

    return remember {
        object : PagingDataDiffer<T>(callback) {
            override suspend fun presentNewList(
                previousList: NullPaddedList<T>,
                newList: NullPaddedList<T>,
                lastAccessedIndex: Int,
                onListPresentable: () -> Unit
            ): Int? = currentOnPresentNewList(this, onListPresentable)
        }
    }
}

@SuppressLint("RestrictedApi")
@Composable
fun <T : Any> rememberLoadStatesWithLifecycle(
    differ: PagingDataDiffer<T>,
    state: Lifecycle.State = Lifecycle.State.STARTED,
): State<CombinedLoadStates> {
    val lifecycleOwner = LocalLifecycleOwner.current
    val initialValue = remember {
        CombinedLoadStates(
            refresh = LoadState.NotLoading(endOfPaginationReached = true),
            prepend = LoadState.NotLoading(endOfPaginationReached = true),
            append = LoadState.NotLoading(endOfPaginationReached = true),
            source = LoadStates(
                refresh = LoadState.NotLoading(endOfPaginationReached = true),
                prepend = LoadState.NotLoading(endOfPaginationReached = true),
                append = LoadState.NotLoading(endOfPaginationReached = true),
            ),
        )
    }

    return produceState(initialValue, lifecycleOwner) {
        lifecycleOwner.repeatOnLifecycle(state) {
            differ.loadStateFlow.filterNotNull().collect {
                value = it
            }
        }
    }
}

@SuppressLint("RestrictedApi")
@Composable
fun <T : Any> Flow<PagingData<T>>.collectItemSnapshotListWithLifecycle(): Pair<ItemSnapshotList<T>, CombinedLoadStates> {
    var snapshot by remember {
        mutableStateOf(
            ItemSnapshotList<T>(
                placeholdersBefore =  0,
                placeholdersAfter = 0,
                items = emptyList()
            )
        )
    }

    // TODO: find anyway to update snapshot state from here
    val callback = rememberDifferCallback(
        onRemoved = { position, count ->
            Log.d("WithLifecycle:WithLifecycle:onRemoved", "$position, $count")
        },
        onInserted = { position, count ->
            Log.d("WithLifecycle:WithLifecycle:onInserted", "$position, $count")
        }
    ) { position, count ->
        Log.d("WithLifecycle:WithLifecycle:onChanged", "$position, $count")
    }

    val differ = rememberPagingDataDiffer<T>(callback) { differ, onListPresentable ->
        Log.d("WithLifecycle:rememberPagingDataDiffer:onPresentNewList", "CALLED")
        onListPresentable()
        snapshot = differ.snapshot()
        null
    }

    val loadStates by rememberLoadStatesWithLifecycle(differ)

    LaunchRepeatOnLifecycle {
        this@collectItemSnapshotListWithLifecycle.collectLatest {
            differ.collectFrom(it)
        }
    }

    return Pair(snapshot, loadStates)
}