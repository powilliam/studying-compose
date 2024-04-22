package com.powilliam.studyingcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import com.powilliam.studyingcompose.navigation.ApplicationNavHost
import com.powilliam.studyingcompose.theming.ApplicationTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge();
        setContent {
            val windowSizeClass = calculateWindowSizeClass(this)

            ApplicationTheme({ this }) {
                ApplicationNavHost(windowSizeClass)
            }
        }
    }
}