package com.powilliam.studyingcompose.wearable.hello

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.wear.compose.material.CompactChip
import androidx.wear.compose.material.Scaffold
import androidx.wear.compose.material.Text

private val modifier = Modifier

@Composable
fun HelloScreen(onNavigateToInstructions: () -> Unit = {}) {
    Scaffold {
        Column(
            modifier = modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(text = "Hello, Wear OS")

            CompactChip(
                onClick = onNavigateToInstructions,
                label = {
                    Text(text = "See instructions")
                },
            )
        }
    }
}