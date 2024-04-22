package com.powilliam.studyingcompose.navigation

import androidx.navigation.NamedNavArgument

sealed class Destination(
    val route: String,
    val arguments: List<NamedNavArgument> = emptyList()
) {
    object Instructions : Destination("instructions")
}
