package com.powilliam.studyingcompose.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.CoroutineScope

@Composable
fun LaunchRepeatOnLifecycle(
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
    state: Lifecycle.State = Lifecycle.State.STARTED,
    onLifecycle: suspend CoroutineScope.() -> Unit,
) {
    val currentOnLifecycle by rememberUpdatedState(onLifecycle)

    LaunchedEffect(Unit) {
        lifecycleOwner.repeatOnLifecycle(state, currentOnLifecycle)
    }
}