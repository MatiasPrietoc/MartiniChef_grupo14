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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.martinichef_grupo14.data.UsuarioDataStore
import com.example.martinichef_grupo14.model.PerfilGuardado
import com.example.martinichef_grupo14.repository.RecetasRepository
import com.example.martinichef_grupo14.ui.components.FotoReceta
import com.example.martinichef_grupo14.ui.components.ImagenInteligente
import com.example.martinichef_grupo14.ui.utils.obtenerWindowSizeClass
import com.example.martinichef_grupo14.viewmodel.EstadoViewModel
import com.example.martinichef_grupo14.viewmodel.PerfilViewModel
import java.io.IOException

@Composable
fun PantallaPrincipal(
    modifier: Modifier = Modifier,
    navController: NavController? = null,
    viewModel: EstadoViewModel = viewModel(),
    perfilViewModel: PerfilViewModel = viewModel()
) {
    val estado by viewModel.activo.collectAsState()
    val mostrarMensaje by viewModel.mostrarMensaje.collectAsState()
    val guardando by viewModel.guardando.collectAsState()
    val error by viewModel.error.collectAsState()
    val imagenUri by perfilViewModel.imagenUri.collectAsState()

    val context = LocalContext.current.applicationContext
    val usuarioDataStore = remember(context) {
        UsuarioDataStore(context)
    }

    var errorPerfil by remember {
        mutableStateOf<String?>(null)
    }

    // Observa el perfil guardado, no los campos sin guardar del formulario.
    val perfil by produceState<PerfilGuardado?>(
        initialValue = null,
        key1 = usuarioDataStore
    ) {
        try {
            usuarioDataStore.perfilFlow.collect {
                value = it
            }
        } catch (_: IOException) {
            errorPerfil = "No se pudo recuperar tu nombre."
        }
    }

    val primerNombre = perfil?.nombre
        ?.trim()
        ?.split(Regex("\\s+"))
        ?.firstOrNull()
        .orEmpty()

    val recetaDestacada = remember {
        RecetasRepository.buscarPorId(3)
            ?: RecetasRepository.obtenerRecetas().firstOrNull()
    }

    val tipoPantalla = obtenerWindowSizeClass().widthSizeClass

    if (estado == null) {
        Box(
            modifier = modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
        return
    }

    val estaActivo = estado == true
    val colores = MaterialTheme.colorScheme

    val colorAnimado by animateColorAsState(
        targetValue = if (estaActivo) {
            colores.primary
        } else {
            colores.secondary
        },
        animationSpec = tween(500),
        label = "colorConsejos"
    )

    val contenido: @Composable () -> Unit = {
        Column(
            modifier = modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "MI COCINA",
                style = MaterialTheme.typography.labelLarge,
                color = colores.primary
            )

            ImagenInteligente(
                imagenUri = imagenUri
            )

            Text(
                text = if (primerNombre.isBlank()) {
                    "¡Bienvenido a tu cocina!"
                } else {
                    "¡Hola, $primerNombre!"
                },
                style = MaterialTheme.typography.headlineMedium,
                textAlign = TextAlign.Center
            )

            Text(
                text = "¿Qué cocinamos hoy?",
                style = MaterialTheme.typography.titleLarge,
                textAlign = TextAlign.Center,
                color = colores.onSurfaceVariant
            )

            TextButton(
                onClick = {
                    navController?.navigate("perfil") {
                        launchSingleTop = true
                    }
                }
            ) {
                Text(
                    if (imagenUri == null) {
                        "Agregar mi foto de perfil"
                    } else {
                        "Cambiar mi foto de perfil"
                    }
                )
            }

            errorPerfil?.let {
                Text(
                    text = it,
                    color = colores.error
                )
            }

            recetaDestacada?.let { receta ->
                ElevatedCard(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(20.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Text(
                            text = "Nuestra propuesta de hoy",
                            style = MaterialTheme.typography.titleMedium,
                            color = colores.primary
                        )

                        FotoReceta(receta = receta)

                        Text(
                            text = receta.titulo,
                            style = MaterialTheme.typography.headlineSmall
                        )

                        Text(
                            text = receta.descripcion,
                            style = MaterialTheme.typography.bodyLarge
                        )

                        Text(
                            text = "${receta.tiempoMinutos} minutos de preparación",
                            style = MaterialTheme.typography.labelLarge,
                            color = colores.onSurfaceVariant
                        )

                        Button(
                            onClick = {
                                navController?.navigate(
                                    "detalle_receta/${receta.id}"
                                ) {
                                    launchSingleTop = true
                                }
                            },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Quiero preparar esta receta")
                        }
                    }
                }
            }

            Text(
                text = "Explora y aprende",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedButton(
                onClick = {
                    navController?.navigate("recetas") {
                        launchSingleTop = true
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Explorar todas las recetas")
            }

            OutlinedButton(
                onClick = {
                    navController?.navigate("aprender") {
                        launchSingleTop = true
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Aprender: videos y cursos")
            }

            HorizontalDivider()

            Text(
                text = "Un consejo antes de comenzar",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.fillMaxWidth()
            )

            Button(
                onClick = {
                    viewModel.alternarEstado()
                },
                enabled = !guardando,
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorAnimado,
                    contentColor = if (estaActivo) {
                        colores.onPrimary
                    } else {
                        colores.onSecondary
                    }
                ),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = when {
                        guardando -> "Guardando..."
                        estaActivo -> "Ocultar consejos de cocina"
                        else -> "Mostrar consejos de cocina"
                    }
                )
            }

            AnimatedVisibility(visible = estaActivo) {
                Card(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(20.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Text(
                            text = "Prepara tu espacio",
                            style = MaterialTheme.typography.titleMedium
                        )

                        Text("• Lee la receta completa antes de comenzar.")
                        Text("• Lava y mide los ingredientes.")
                        Text("• Reúne los utensilios que necesitarás.")
                        Text("• Mantén limpia y ordenada tu cocina.")
                    }
                }
            }

            AnimatedVisibility(visible = mostrarMensaje) {
                Text(
                    text = "Tu preferencia quedó guardada",
                    color = colores.primary,
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            error?.let {
                Text(
                    text = it,
                    color = colores.error
                )
            }

            HorizontalDivider()

            Text(
                text = "Tu espacio personal",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedButton(
                onClick = {
                    navController?.navigate("resumen") {
                        launchSingleTop = true
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Mis datos")
            }

            TextButton(
                onClick = {
                    navController?.navigate("settings") {
                        launchSingleTop = true
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Personalizar apariencia")
            }

            Text(
                text = "MartiniChef · Aprende, prepara y disfruta",
                style = MaterialTheme.typography.labelMedium,
                color = colores.onSurfaceVariant,
                textAlign = TextAlign.Center
            )
        }
    }

    when (tipoPantalla) {
        WindowWidthSizeClass.Compact ->
            HomeScreenCompacta(contenido = contenido)

        WindowWidthSizeClass.Medium ->
            HomeScreenMediana(contenido = contenido)

        WindowWidthSizeClass.Expanded ->
            HomeScreenExpandida(contenido = contenido)

        else ->
            HomeScreenCompacta(contenido = contenido)
    }
}