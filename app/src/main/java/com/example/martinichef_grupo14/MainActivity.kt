package com.example.martinichef_grupo14

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Density
import androidx.core.view.WindowCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.martinichef_grupo14.navigation.AppNavigation
import com.example.martinichef_grupo14.ui.theme.MartiniChef_grupo14Theme
import com.example.martinichef_grupo14.viewmodel.AjustesViewModel

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            val ajustesViewModel: AjustesViewModel = viewModel()

            val preferencias by
            ajustesViewModel.preferencias.collectAsState()

            val densidadSistema = LocalDensity.current
            val ajustes = preferencias

            if (ajustes == null) {
                MartiniChef_grupo14Theme(
                    dynamicColor = false
                ) {
                    Surface(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    }
                }
            } else {
                CompositionLocalProvider(
                    LocalDensity provides Density(
                        density = densidadSistema.density,
                        fontScale = densidadSistema.fontScale *
                                ajustes.escalaTexto
                    )
                ) {
                    MartiniChef_grupo14Theme(
                        darkTheme = ajustes.modoOscuro,
                        dynamicColor = false
                    ) {
                        Surface(
                            modifier = Modifier.fillMaxSize(),
                            color = MaterialTheme.colorScheme.background
                        ) {
                            AppNavigation(
                                ajustesViewModel = ajustesViewModel
                            )
                        }
                    }
                }
            }
        }
    }
}