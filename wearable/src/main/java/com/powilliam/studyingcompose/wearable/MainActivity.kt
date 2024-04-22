package com.powilliam.studyingcompose.wearable

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.powilliam.studyingcompose.wearable.navigation.WearableNavHost
import com.powilliam.studyingcompose.wearable.theming.WearableTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            WearableTheme {
                WearableNavHost()
            }
        }
    }
}