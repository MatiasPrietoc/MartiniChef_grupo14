package com.example.martinichef_grupo14

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.martinichef_grupo14.ui.screens.HomeScreenCompacta
import com.example.martinichef_grupo14.ui.screens.HomeScreenExpandida
import com.example.martinichef_grupo14.ui.screens.HomeScreenMediana
import com.example.martinichef_grupo14.ui.theme.MartiniChef_grupo14Theme
import com.example.martinichef_grupo14.ui.utils.obtenerWindowSizeClass

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MartiniChef_grupo14Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AppAdaptativa()
                }
            }
        }
    }
}

@Composable
fun AppAdaptativa() {
    // 1. Calculamos la clase de tamaño actual
    val windowSizeClass = obtenerWindowSizeClass()

    // 2. Evaluamos el ancho de pantalla para seleccionar la vista
    when (windowSizeClass.widthSizeClass) {
        WindowWidthSizeClass.Compact -> HomeScreenCompacta()
        WindowWidthSizeClass.Medium -> HomeScreenMediana()
        WindowWidthSizeClass.Expanded -> HomeScreenExpandida()
        else -> HomeScreenCompacta()
    }
}