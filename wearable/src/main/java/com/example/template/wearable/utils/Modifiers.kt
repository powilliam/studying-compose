package com.example.template.wearable.utils

import androidx.compose.foundation.focusable
import androidx.compose.foundation.gestures.ScrollableState
import androidx.compose.foundation.gestures.animateScrollBy
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.input.rotary.RotaryScrollEvent
import androidx.compose.ui.input.rotary.onRotaryScrollEvent
import androidx.wear.compose.foundation.ExperimentalWearFoundationApi
import androidx.wear.compose.foundation.rememberActiveFocusRequester
import kotlinx.coroutines.launch

@OptIn(ExperimentalWearFoundationApi::class)
@Composable
fun Modifier.onRotaryScroll(
    enabled: Boolean = true,
    action: suspend (RotaryScrollEvent) -> Unit
): Modifier {
    val coroutineScope = rememberCoroutineScope()
    val focusRequester = rememberActiveFocusRequester()

    return this then onRotaryScrollEvent {
        coroutineScope.launch {
            action(it)
        }
        true
    }
        .focusRequester(focusRequester)
        .focusable(enabled)
}

@Composable
fun Modifier.rotaryScrolled(state: ScrollableState, enabled: Boolean = true) =
    onRotaryScroll(enabled) {
        state.animateScrollBy(it.verticalScrollPixels)
    }