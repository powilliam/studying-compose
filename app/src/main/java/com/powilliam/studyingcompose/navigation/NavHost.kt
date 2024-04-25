package com.powilliam.studyingcompose.navigation

import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.powilliam.studyingcompose.stories.ui.StoriesScreen
import com.powilliam.studyingcompose.stories.ui.StoriesViewModel
import com.powilliam.studyingcompose.utils.collectItemSnapshotListWithLifecycle

@Composable
fun ApplicationNavHost(
    controller: NavHostController,
    windowSizeClass: WindowSizeClass,
) {
    NavHost(navController = controller, startDestination = Destination.Stories.route) {
        composable(Destination.Stories.route, Destination.Stories.arguments) {
            val storiesViewModel = hiltViewModel<StoriesViewModel>()
            /**
             *  Compose only triggers recomposition for value changes when the state is a State<T>.
             * A conversion is needed to work with other observable data structure.
             *
             *      Flows -> collectAsStateWithLifecycle() [Android Only] or collectAsState() [Platform Agnostic]
             *      LiveData -> observeAsState()
             *      RXJava2 or RXJava3 -> subscribeAsState()
             */
            val (snapshot, loadStates) = storiesViewModel.paging.collectItemSnapshotListWithLifecycle()

            StoriesScreen(snapshot, loadStates)
        }
    }
}