package com.powilliam.studyingcompose.stories.ui

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.powilliam.studyingcompose.theming.ApplicationTheme

private val modifier: Modifier = Modifier

/**
 *  Compose is a Declarative UI Framework
 * for building native interfaces on Android.
 *
 * - As `declarative` implies, it's based on the `f(data) = ui` thing
 * which means that composable functions outputs interfaces from any given data
 *
 * - The `@Composable` annotation informs the compiler that the annotated function
 * will convert data into ui
 *
 * - Composable functions don't return anything. That's because they are meant to describe
 * a interface hierarchy instead of instructing how to construct it
 *
 * - Composable functions should be fast to avoid junk as frames passes
 *
 * - Recomposition is the process of calling composable functions
 * again when any given data changes
 *
 * - Recomposition skips as many composables and lambdas as possible,
 * `smart recomposition` as they describe in the docs, so composables
 * can be called again at every frame without suffering a complete redrawn.
 *
 * Keep composables free from Side Effects
 */
@Composable
fun StoriesScreen(storiesState: StoriesUiState = StoriesUiState()) {
    Scaffold { paddingValues ->
        Column(modifier = modifier.padding(paddingValues)) {
            Text(text = "Greetings from StoriesScreen")
        }
    }
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun StoriesScreenPreview() {
    val context = LocalContext.current

    ApplicationTheme({ context }) {
        StoriesScreen()
    }
}