package com.example.martinichef_grupo14

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.view.WindowCompat
import com.example.martinichef_grupo14.navigation.AppNavigation
import com.example.martinichef_grupo14.ui.theme.MartiniChef_grupo14Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            MartiniChef_grupo14Theme {
                AppNavigation()
            }
        }
    }
}