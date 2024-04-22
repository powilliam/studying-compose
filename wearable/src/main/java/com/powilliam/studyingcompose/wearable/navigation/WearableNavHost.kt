package com.powilliam.studyingcompose.wearable.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.wear.compose.navigation.SwipeDismissableNavHost
import androidx.wear.compose.navigation.composable
import androidx.wear.compose.navigation.rememberSwipeDismissableNavController
import com.powilliam.studyingcompose.wearable.hello.HelloScreen
import com.powilliam.studyingcompose.wearable.instructions.InstructionsScreen

@Composable
fun WearableNavHost(controller: NavHostController = rememberSwipeDismissableNavController()) {
    SwipeDismissableNavHost(
        navController = controller,
        startDestination = Destination.Hello.route
    ) {
        composable(Destination.Hello.route) {
            HelloScreen {
                controller.navigate(Destination.Instructions.route)
            }
        }

        composable(Destination.Instructions.route) {
            InstructionsScreen()
        }
    }
}