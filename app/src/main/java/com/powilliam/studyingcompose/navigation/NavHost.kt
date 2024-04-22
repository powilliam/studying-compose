package com.powilliam.studyingcompose.navigation

import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.powilliam.studyingcompose.stories.ui.StoriesScreen

@Composable
fun ApplicationNavHost(
    windowSizeClass: WindowSizeClass,
    controller: NavHostController = rememberNavController()
) {
    NavHost(navController = controller, startDestination = Destination.Stories.route) {
        composable(Destination.Stories.route, Destination.Stories.arguments) {
            StoriesScreen()
        }
    }
}