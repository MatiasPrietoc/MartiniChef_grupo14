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
import com.example.martinichef_grupo14.ui.utils.obtenerWindowSizeClass
import com.example.martinichef_grupo14.viewmodel.EstadoViewModel

@Composable
fun PantallaPrincipal(
    modifier: Modifier = Modifier,
    navController: NavController? = null,
    viewModel: EstadoViewModel = viewModel()
) {
    val estado = viewModel.activo.collectAsState()
    val mostrarMensaje = viewModel.mostrarMensaje.collectAsState()

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

        val colorAnimado by animateColorAsState(
            targetValue = if (estaActivo) {
                Color(0xFF4CAF50)
            } else {
                Color(0xFFBBBEC5)
            },
            animationSpec = tween(durationMillis = 500),
            label = "colorAnimacion"
        )

        val textoBoton by remember(estaActivo) {
            derivedStateOf {
                if (estaActivo) "Desactivar" else "Activar"
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
                // 1. Botón de Activar / Desactivar
                Button(
                    onClick = {
                        viewModel.alternarEstado()
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorAnimado
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

                AnimatedVisibility(visible = mostrarMensaje.value) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = "¡Estado guardado exitosamente!",
                            color = Color(0xFF4CAF50),
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }

                Spacer(modifier = Modifier.height(28.dp))

                Text(
                    text = "Módulos de la aplicación",
                    style = MaterialTheme.typography.titleMedium
                )

                Spacer(modifier = Modifier.height(12.dp))

                // 2. Botón ir a Registro
                Button(
                    onClick = {
                        navController?.navigate("registro")
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Ir a Registro")
                }

                Spacer(modifier = Modifier.height(8.dp))

                // 3. Botón ir a Perfil
                Button(
                    onClick = {
                        navController?.navigate("perfil")
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Ir a Perfil")
                }

                Spacer(modifier = Modifier.height(8.dp))

                // 4. Botón ir a Resumen
                Button(
                    onClick = {
                        navController?.navigate("resumen")
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Ir a Resumen")
                }

                Spacer(modifier = Modifier.height(8.dp))

                // 5. Botón ir a Recetas
                Button(
                    onClick = {
                        navController?.navigate("recetas")
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Ir a Recetas")
                }

                Spacer(modifier = Modifier.height(8.dp))

                // 6. Botón ir a Ajustes
                OutlinedButton(
                    onClick = {
                        navController?.navigate("settings")
                    },
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