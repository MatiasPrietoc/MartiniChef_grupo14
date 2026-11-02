package com.example.martinichef_grupo14.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.martinichef_grupo14.ui.components.ImagenInteligente
import com.example.martinichef_grupo14.ui.utils.obtenerWindowSizeClass
import com.example.martinichef_grupo14.viewmodel.EstadoViewModel
import com.example.martinichef_grupo14.viewmodel.PerfilViewModel

@Composable
fun PantallaPrincipal(
    modifier: Modifier = Modifier,
    navController: NavController? = null,
    viewModel: EstadoViewModel = viewModel(),
    perfilViewModel: PerfilViewModel = viewModel()
) {
    val estado = viewModel.activo.collectAsState()
    val mostrarMensaje = viewModel.mostrarMensaje.collectAsState()
    val guardando = viewModel.guardando.collectAsState()
    val error = viewModel.error.collectAsState()
    val imagenUri by perfilViewModel.imagenUri.collectAsState()

    val windowSizeClass = obtenerWindowSizeClass()
    val tipoPantalla = windowSizeClass.widthSizeClass

    if (estado.value == null) {
        Box(
            modifier = modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else {
        val estaActivo = estado.value!!

        val colores = MaterialTheme.colorScheme

        val colorAnimado by animateColorAsState(
            targetValue = if (estaActivo) {
                colores.primary
            } else {
                colores.secondary
            },
            animationSpec = tween(durationMillis = 500),
            label = "colorAnimacion"
        )

        val textoBoton by remember(estaActivo) {
            derivedStateOf {
                if (estaActivo) {
                    "Ocultar consejos de cocina"
                } else {
                    "Mostrar consejos de cocina"
                }
            }
        }

        val contenido: @Composable () -> Unit = {
            Column(
                modifier = modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(24.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Foto compartida con la pantalla de perfil.
                ImagenInteligente(
                    imagenUri = imagenUri
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Preferencia persistente de consejos de cocina.
                Button(
                    onClick = { viewModel.alternarEstado() },
                    enabled = !guardando.value,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorAnimado,
                        contentColor = if (estaActivo) {
                            colores.onPrimary
                        } else {
                            colores.onSecondary
                        }
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                ) {
                    Text(
                        text = textoBoton,
                        style = MaterialTheme.typography.titleMedium
                    )
                }

                AnimatedVisibility(visible = estaActivo) {
                    Column {
                        Spacer(modifier = Modifier.height(16.dp))

                        Card(
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(
                                modifier = Modifier.padding(16.dp),
                                verticalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Text(
                                    text = "Antes de comenzar",
                                    style = MaterialTheme.typography.titleMedium
                                )

                                Text("• Lee todos los pasos de la receta.")
                                Text("• Prepara y mide los ingredientes.")
                                Text("• Reúne los utensilios necesarios.")
                                Text("• Mantén ordenado tu espacio de trabajo.")
                            }
                        }
                    }
                }

                AnimatedVisibility(visible = mostrarMensaje.value) {
                    Column {
                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = "¡Estado guardado exitosamente!",
                            color = MaterialTheme.colorScheme.primary,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }

                error.value?.let { mensaje ->
                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = mensaje,
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }

                Spacer(modifier = Modifier.height(28.dp))

                Text(
                    text = "Explora MartiniChef",
                    style = MaterialTheme.typography.titleMedium
                )

                Spacer(modifier = Modifier.height(12.dp))

                Button(
                    onClick = { navController?.navigate("registro") },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Ir a Registro")
                }

                Spacer(modifier = Modifier.height(8.dp))

                Button(
                    onClick = { navController?.navigate("perfil") },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Foto de perfil")
                }

                Spacer(modifier = Modifier.height(8.dp))

                Button(
                    onClick = { navController?.navigate("resumen") },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Ir a Resumen")
                }

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedButton(
                    onClick = { navController?.navigate("settings") },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Ir a Ajustes")
                }
            }
        }

        when (tipoPantalla) {
            WindowWidthSizeClass.Compact -> {
                HomeScreenCompacta(contenido = contenido)
            }

            WindowWidthSizeClass.Medium -> {
                HomeScreenMediana(contenido = contenido)
            }

            WindowWidthSizeClass.Expanded -> {
                HomeScreenExpandida(contenido = contenido)
            }

            else -> {
                HomeScreenCompacta(contenido = contenido)
            }
        }
    }
}