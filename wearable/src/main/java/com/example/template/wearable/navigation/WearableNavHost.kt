package com.example.template.wearable.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.wear.compose.navigation.SwipeDismissableNavHost
import androidx.wear.compose.navigation.composable
import androidx.wear.compose.navigation.rememberSwipeDismissableNavController
import com.example.template.wearable.hello.HelloScreen
import com.example.template.wearable.instructions.InstructionsScreen

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