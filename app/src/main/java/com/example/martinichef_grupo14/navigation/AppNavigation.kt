package com.example.martinichef_grupo14.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.martinichef_grupo14.ui.screens.RegistroScreen
import com.example.martinichef_grupo14.ui.screens.ResumenScreen
import com.example.martinichef_grupo14.viewmodel.UsuarioViewModel

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    // Creamos la instancia del ViewModel una sola vez para compartirla entre pantallas
    val usuarioViewModel: UsuarioViewModel = viewModel()

    NavHost(
        navController = navController,
        startDestination = "registro"
    ) {
        composable(route = "registro") {
            RegistroScreen(navController = navController, viewModel = usuarioViewModel)
        }
        composable(route = "resumen") {
            ResumenScreen(navController = navController, viewModel = usuarioViewModel)
        }
    }
}