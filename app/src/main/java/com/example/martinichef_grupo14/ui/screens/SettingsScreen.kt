package com.example.martinichef_grupo14.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.martinichef_grupo14.viewmodel.AjustesViewModel
import kotlin.math.abs

@Composable
fun SettingsScreen(
    navController: NavController,
    viewModel: AjustesViewModel
) {
    val preferencias by viewModel.preferencias.collectAsState()
    val error by viewModel.error.collectAsState()

    val ajustes = preferencias

    if (ajustes == null) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
        return
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Ajustes",
            style = MaterialTheme.typography.headlineMedium
        )

        Text(
            text = "Haz que MartiniChef se sienta como tu cocina.",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        HorizontalDivider()

        Text(
            text = "Apariencia",
            style = MaterialTheme.typography.titleLarge
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            FilterChip(
                selected = !ajustes.modoOscuro,
                onClick = {
                    viewModel.cambiarModoOscuro(false)
                },
                label = {
                    Text("Modo día")
                },
                modifier = Modifier.weight(1f)
            )

            FilterChip(
                selected = ajustes.modoOscuro,
                onClick = {
                    viewModel.cambiarModoOscuro(true)
                },
                label = {
                    Text("Modo noche")
                },
                modifier = Modifier.weight(1f)
            )
        }

        Text(
            text = "Tamaño del texto",
            style = MaterialTheme.typography.titleLarge
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            val opciones = listOf(
                "Pequeño" to 0.9f,
                "Normal" to 1f,
                "Grande" to 1.2f
            )

            opciones.forEach { (nombre, escala) ->
                FilterChip(
                    selected = abs(
                        ajustes.escalaTexto - escala
                    ) < 0.01f,
                    onClick = {
                        viewModel.cambiarEscalaTexto(escala)
                    },
                    label = {
                        Text(nombre)
                    },
                    modifier = Modifier.weight(1f)
                )
            }
        }

        Card(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "Así se verá tu texto",
                    style = MaterialTheme.typography.titleMedium
                )

                Text(
                    text = "Prepara tus ingredientes y disfruta " +
                            "aprendiendo a cocinar con MartiniChef.",
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }

        error?.let { mensaje ->
            Text(
                text = mensaje,
                color = MaterialTheme.colorScheme.error
            )
        }

        HorizontalDivider()

        Text(
            text = "Mi cuenta",
            style = MaterialTheme.typography.titleLarge
        )

        OutlinedButton(
            onClick = {
                navController.navigate("resumen") {
                    launchSingleTop = true
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Ver y editar mis datos")
        }

        OutlinedButton(
            onClick = {
                navController.navigate("perfil") {
                    launchSingleTop = true
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Cambiar mi foto de perfil")
        }

        HorizontalDivider()

        Button(
            onClick = {
                navController.popBackStack(
                    route = "pantalla_principal",
                    inclusive = false
                )
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Volver al inicio")
        }

        OutlinedButton(
            onClick = {
                navController.navigate("login") {
                    // Elimina las pantallas anteriores del historial.
                    popUpTo(navController.graph.id) {
                        inclusive = false
                    }

                    launchSingleTop = true
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Cerrar sesión")
        }

        Text(
            text = "Al cerrar sesión conservarás tu perfil, " +
                    "tu foto y tus preferencias en este dispositivo.",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}