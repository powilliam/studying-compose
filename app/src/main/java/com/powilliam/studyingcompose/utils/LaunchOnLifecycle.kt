package com.powilliam.studyingcompose.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner

typealias OnStateChanged = (LifecycleOwner, Lifecycle.Event) -> Unit

@Composable
fun rememberLifecycleObserver(onStateChanged: OnStateChanged): LifecycleEventObserver {
    /**
     * rememberUpdatedState will do its work to keep onStateChanged updated
     * but won't cancel and restart its execution during recompositions
     */
    val currentOnStateChanged by rememberUpdatedState(onStateChanged)

    return remember {
        LifecycleEventObserver(currentOnStateChanged)
    }
}

@Composable
fun LaunchOnLifecycle(
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
    onStateChanged: OnStateChanged
) {
    val observer = rememberLifecycleObserver(onStateChanged)

    DisposableEffect(Unit) {
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }
}