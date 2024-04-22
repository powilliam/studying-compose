package com.example.template.wearable.theming

import androidx.compose.runtime.Composable
import androidx.wear.compose.material.MaterialTheme

@Composable
fun WearableTheme(content: @Composable () -> Unit) {
    MaterialTheme(colors = defaultColors) {
        content()
    }
}