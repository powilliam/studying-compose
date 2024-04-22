package com.powilliam.studyingcompose.wearable.navigation

sealed class Destination(val route: String) {
    object Hello : Destination("hello")
    object Instructions : Destination("instructions")
}