package com.example.martinichef_grupo14.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.martinichef_grupo14.ui.screens.*
import com.example.martinichef_grupo14.viewmodel.*

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    val estadoViewModel: EstadoViewModel = viewModel()
    val mainViewModel: MainViewModel = viewModel()
    val usuarioViewModel: UsuarioViewModel = viewModel()

    NavHost(
        navController = navController,
        startDestination = "pantalla_principal"
    ) {
        composable("pantalla_principal") {
            PantallaPrincipal(
                navController = navController,
                viewModel = estadoViewModel
            )
        }

        composable("registro") {
            RegistroScreen(
                navController = navController,
                viewModel = usuarioViewModel
            )
        }

        composable("perfil") {
            ProfileScreen(navController = navController)
        }

        composable("resumen") {
            ResumenScreen(
                navController = navController,
                viewModel = usuarioViewModel
            )
        }

        composable("settings") {
            SettingsScreen(
                navController = navController,
                viewModel = mainViewModel
            )
        }

        composable("recetas") {
            RecetasScreen(
                onVolver = {
                    navController.popBackStack()
                }
            )
        }
    }
}