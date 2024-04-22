package com.powilliam.studyingcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import com.powilliam.studyingcompose.navigation.ApplicationNavHost
import com.powilliam.studyingcompose.theming.ApplicationTheme
import dagger.hilt.android.AndroidEntryPoint

/**
 * Compose is a Declarative UI Framework
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
 * - Composables can compose at any order, they aren't procedural
 *
 * - Composables can compose in parallel. It's important to every app state
 * be thread-safe and free from Side Effects, so the compiler can better use
 * CPU resources and delegate multiple threads to the composition phase
 *
 * - Recomposition is the process of calling composable functions
 * again when any given data changes
 *
 * - Recomposition skips as many composables and lambdas as possible,
 * `smart recomposition` as they describe in the docs, so composables
 * can be called again at every frame without suffering a complete redrawn.
 *
 * - Keep composables free from Side Effects
 *
 * State is any value that can and will change over time.
 *
 * - The only way to trigger a recomposition is calling the same composable
 * but with new values for any given parameter
 *
 * - remember {} is an API that aims to save both mutable and immutable objects in memory
 * so the saved object can survive recompositions
 *
 * - remember {} accepts keys, so everytime a key changes it will recalculate the lambda
 *
 * - remember {} can't survive configurations changes. For this case, it's better to
 * use a ViewModel or rememberSaveable {} for values that can be saved
 * in a Bundle otherwise with a custom Saver like:
 *
 * @Parcelize
 * data class Entity(...) : Parcelable
 *
 * OR
 *
 * val entitySaver = mapSaver(save = ..., restore = ...)
 * val entitiesSaver = listSaver(save = ..., restore = ...)
 *
 * - mutableStateOf<T>() creates an instance of a MutableState<T> which extends a State<T>.
 * It does represent an observable type integrated with the compose runtime.
 * Any changes to its value wil trigger a recomposition.
 *
 * These are all valid uses:
 *
 * val mutableState = remember { mutableStateOf() }
 * var value by remember { mutableStateOf() } // needs androidx.compose.runtime.getValue and androidx.compose.runtime.setValue
 * val (value, setValue) = remember { mutableStateOf() }
 *
 * - Compose only triggers recomposition for value changes when the state is a State<T>.
 * A conversion is needed to work with other observable data structure.
 *
 * Flows -> collectAsStateWithLifecycle() [Android Only] or collectAsState() [Platform Agnostic]
 * LiveData -> observeAsState()
 * RXJava2 or RXJava3 -> subscribeAsState()
 *
 * - Stateful composables are composables that holds its own state.
 * In the other hand, stateless composables are composables that doesn't hold its own state
 *
 * - Stateless composables are preferable so they can be reusable and testable
 *
 * - Stateless composables are achieved by hoisting the state
 *
 * State hoisting is a pattern that converts stateful composables into stateless
 * by delegating the state holding responsibility up to its parent.
 *
 * Composition describes the ui and is produced by running composables.
 * It's a tree-structure of all composables that describe the ui.
 *
 * The whole lifecycle can be described by:
 * Enter the composition -> Recompose N' times -> Leave the composition
 *
 * The tree-structure can be represented by:
 * Greetings ->
 *   Column ->
 *       Text ->
 *       Text ->
 *
 * - Recompositions are triggered by any change to a State<T>
 */
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge();
        setContent {
            val windowSizeClass = calculateWindowSizeClass(this)

            ApplicationTheme({ this }) {
                ApplicationNavHost(windowSizeClass)
            }
        }
    }
}