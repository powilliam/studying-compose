package com.powilliam.studyingcompose.wearable.instructions

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.wear.compose.foundation.lazy.ScalingLazyColumn
import androidx.wear.compose.foundation.lazy.ScalingLazyListAnchorType
import androidx.wear.compose.foundation.lazy.itemsIndexed
import androidx.wear.compose.foundation.lazy.rememberScalingLazyListState
import androidx.wear.compose.material.Card
import androidx.wear.compose.material.PositionIndicator
import androidx.wear.compose.material.Scaffold
import androidx.wear.compose.material.Text
import androidx.wear.compose.material.Vignette
import androidx.wear.compose.material.VignettePosition
import com.powilliam.studyingcompose.wearable.utils.rotaryScrolled

private val instructions = listOf(
    "Edit rootProject.name on settings.gradle.kts",
    "Edit android namespace and applicationId on wearable/build.gradle.kts",
    "Edit wearable/src/main/java folder structure according to your package name",
    "Start coding"
)

private val modifier = Modifier

@Composable
fun InstructionsScreen() {
    val scalingLazyListState = rememberScalingLazyListState()
    Scaffold(
        positionIndicator = {
            PositionIndicator(scalingLazyListState = scalingLazyListState)
        },
        vignette = {
            Vignette(vignettePosition = VignettePosition.TopAndBottom)
        }
    ) {
        ScalingLazyColumn(
            modifier = modifier.rotaryScrolled(scalingLazyListState),
            state = scalingLazyListState,
            anchorType = ScalingLazyListAnchorType.ItemStart,
        ) {
            itemsIndexed(
                instructions,
                key = { _, instruction -> instruction }
            ) { index, instruction ->
                Card(onClick = {}) {
                    Text(text = "${index + 1} - $instruction")
                }
            }
        }
    }
}