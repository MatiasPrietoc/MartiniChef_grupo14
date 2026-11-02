package com.example.martinichef_grupo14.navigation

import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.navArgument
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.martinichef_grupo14.repository.RecetasRepository
import com.example.martinichef_grupo14.ui.components.BarraNavegacion
import com.example.martinichef_grupo14.ui.screens.*
import com.example.martinichef_grupo14.viewmodel.*
import com.example.martinichef_grupo14.repository.AprendizajeRepository

@Composable
fun AppNavigation(
    ajustesViewModel: AjustesViewModel
) {
    val navController = rememberNavController()

    val estadoViewModel: EstadoViewModel = viewModel()
    val usuarioViewModel: UsuarioViewModel = viewModel()
    val loginViewModel: LoginViewModel = viewModel()
    val perfilViewModel: PerfilViewModel = viewModel()
    val loginState by loginViewModel.uiState.collectAsState()
    val entradaActual by navController.currentBackStackEntryAsState()
    val rutaActual = entradaActual?.destination?.route
    val mostrarBarra = rutaActual in listOf(
        "pantalla_principal",
        "recetas",
        "aprender",
        "perfil",
        "settings"
    )

    Scaffold(
        bottomBar = {
            if (mostrarBarra) {
                BarraNavegacion(
                    rutaActual = rutaActual,
                    onNavegar = { destino ->
                        if (rutaActual != destino) {
                            navController.navigate(destino) {
                                popUpTo("pantalla_principal") {
                                    saveState = true
                                }

                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    }
                )
            }
        }
    ) { espacioInterior ->

        NavHost(
            navController = navController,
            startDestination = "intro",
            modifier = Modifier
                .fillMaxSize()
                .padding(espacioInterior)
                .consumeWindowInsets(espacioInterior)
        ) {
            composable("intro") {
                IntroScreen(
                    onTerminar = {
                        navController.navigate("login") {
                            popUpTo("intro") {
                                inclusive = true
                            }

                            launchSingleTop = true
                        }
                    }
                )
            }

            composable("login") {
                LoginScreen(
                    email = loginState.email,
                    password = loginState.password,
                    error = loginState.error,
                    cargando = loginState.cargando,
                    onEmailChanged = {
                        loginViewModel.onEmailChanged(it)
                    },
                    onPasswordChanged = {
                        loginViewModel.onPasswordChanged(it)
                    },
                    onIngresar = {
                        loginViewModel.ingresar {
                            navController.navigate("pantalla_principal") {
                                popUpTo("login") {
                                    inclusive = true
                                }

                                launchSingleTop = true
                            }
                        }
                    },
                    onRegistro = {
                        navController.navigate("registro")
                    }
                )
            }

            composable("pantalla_principal") {
                PantallaPrincipal(
                    navController = navController,
                    viewModel = estadoViewModel,
                    perfilViewModel = perfilViewModel
                )
            }

            composable("registro") {
                RegistroScreen(
                    navController = navController,
                    viewModel = usuarioViewModel
                )
            }

            composable("recetas") {
                RecetasScreen(
                    recetas = RecetasRepository.obtenerRecetas(),
                    onVerReceta = { id ->
                        navController.navigate("detalle_receta/$id")
                    }
                )
            }

            composable(
                route = "detalle_receta/{id}",
                arguments = listOf(
                    navArgument("id") {
                        type = NavType.IntType
                    }
                )
            ) { entrada ->
                val id = entrada.arguments?.getInt("id")

                val receta = id?.let {
                    RecetasRepository.buscarPorId(it)
                }

                DetalleRecetaScreen(
                    receta = receta,
                    onVolver = {
                        navController.popBackStack()
                    }
                )
            }
            composable("aprender") {
                AprenderScreen(
                    contenidos = AprendizajeRepository.obtenerContenidos(),
                    onVerContenido = { id ->
                        navController.navigate("detalle_aprendizaje/$id")
                    }
                )
            }

            composable(
                route = "detalle_aprendizaje/{id}",
                arguments = listOf(
                    navArgument("id") {
                        type = NavType.IntType
                    }
                )
            ) { entrada ->
                val id = entrada.arguments?.getInt("id")

                val contenido = id?.let {
                    AprendizajeRepository.buscarPorId(it)
                }

                DetalleAprendizajeScreen(
                    contenido = contenido,
                    onVolver = {
                        navController.popBackStack()
                    }
                )
            }

            composable("perfil") {
                ProfileScreen(
                    navController = navController,
                    viewModel = perfilViewModel
                )
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
                    viewModel = ajustesViewModel
                )
            }
        }
    }
}