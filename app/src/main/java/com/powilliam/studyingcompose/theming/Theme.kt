package com.powilliam.studyingcompose.theming

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable

@Composable
fun ApplicationTheme(content: @Composable () -> Unit) {
    MaterialTheme(colorScheme = lightScheme) {
        content()
    }
}