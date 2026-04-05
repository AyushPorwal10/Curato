package com.curato.wallpapers

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import com.curato.wallpapers.ui.navigation.CuratoNavGraph
import com.curato.wallpapers.ui.theme.CuratoTheme
import com.curato.wallpapers.ui.theme.SurfaceBase
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CuratoTheme {
                CuratoNavGraph(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(SurfaceBase),
                )
            }
        }
    }
}
