package com.powilliam.studyingcompose.theming

import android.annotation.SuppressLint
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

private val isDynamicColorSchemeSupported = Build.VERSION.SDK_INT >= Build.VERSION_CODES.S

@SuppressLint("NewApi")
@Composable
fun ApplicationTheme(
    isDarkModeEnabled: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val ctx = LocalContext.current
    val colorScheme = when {
        !isDarkModeEnabled and isDynamicColorSchemeSupported -> dynamicLightColorScheme(ctx)
        isDarkModeEnabled and isDynamicColorSchemeSupported -> dynamicDarkColorScheme(ctx)
        isDarkModeEnabled and !isDynamicColorSchemeSupported -> applicationDarkColors
        else -> applicationLightColors
    }

    MaterialTheme(colorScheme) {
        content()
    }
}