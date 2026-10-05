package com.curato.wallpapers

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.curato.wallpapers.ui.navigation.CuratoNavGraph
import com.curato.wallpapers.ui.theme.CuratoTheme
import com.curato.wallpapers.ui.theme.LocalThemeViewModel
import com.curato.wallpapers.ui.theme.ThemeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val themeViewModel: ThemeViewModel = hiltViewModel()
            val themePreference by themeViewModel.isDarkTheme.collectAsStateWithLifecycle()
            // null = not set yet → fall back to OS setting
            val isDark = themePreference ?: isSystemInDarkTheme()

            CompositionLocalProvider(LocalThemeViewModel provides themeViewModel) {
                CuratoTheme(darkTheme = isDark) {
                    CuratoNavGraph(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(MaterialTheme.colorScheme.background),
                    )
                }
            }
        }
    }
}
