package com.example.template.wearable.navigation

sealed class Destination(val route: String) {
    object Hello : Destination("hello")
    object Instructions : Destination("instructions")
}