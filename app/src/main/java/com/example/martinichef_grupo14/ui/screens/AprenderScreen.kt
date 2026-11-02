package com.example.martinichef_grupo14.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayCircle
import androidx.compose.material.icons.filled.School
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.martinichef_grupo14.model.ContenidoAprendizaje
import com.example.martinichef_grupo14.model.TipoContenido

@Composable
fun AprenderScreen(
    contenidos: List<ContenidoAprendizaje>,
    onVerContenido: (Int) -> Unit,
    onVolverInicio: () -> Unit
) {
    var filtro by rememberSaveable {
        mutableStateOf("Todos")
    }

    val contenidosVisibles = contenidos.filter { contenido ->
        when (filtro) {
            "Videos" -> contenido.tipo == TipoContenido.VIDEO
            "Cursos" -> contenido.tipo == TipoContenido.CURSO
            else -> true
        }
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        TextButton(
            onClick = onVolverInicio,
            modifier = Modifier.padding(
                horizontal = 16.dp,
                vertical = 8.dp
            )
        ) {
            Text("← Volver al inicio")
        }

        HorizontalDivider()

        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            contentPadding = PaddingValues(24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Text(
                    text = "Aprende con MartiniChef",
                    style = MaterialTheme.typography.headlineMedium
                )

                Spacer(Modifier.height(8.dp))

                Text(
                    text = "Descubre ideas y técnicas para comenzar a cocinar.",
                    style = MaterialTheme.typography.bodyLarge
                )

                Spacer(Modifier.height(8.dp))

                Text(
                    text = "Guías de videos y cursos simulados",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Spacer(Modifier.height(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    listOf("Todos", "Videos", "Cursos").forEach { opcion ->
                        FilterChip(
                            selected = filtro == opcion,
                            onClick = {
                                filtro = opcion
                            },
                            label = {
                                Text(opcion)
                            },
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            }

            items(
                items = contenidosVisibles,
                key = { it.id }
            ) { contenido ->
                val esVideo = contenido.tipo == TipoContenido.VIDEO

                ElevatedCard(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(20.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Icon(
                                imageVector = if (esVideo) {
                                    Icons.Default.PlayCircle
                                } else {
                                    Icons.Default.School
                                },
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary
                            )

                            Text(
                                text = if (esVideo) {
                                    "Video simulado"
                                } else {
                                    "Curso simulado"
                                },
                                style = MaterialTheme.typography.labelLarge,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }

                        Text(
                            text = contenido.titulo,
                            style = MaterialTheme.typography.titleLarge
                        )

                        Text(
                            text = contenido.descripcion,
                            style = MaterialTheme.typography.bodyMedium
                        )

                        Text(
                            text = "Duración estimada: " +
                                    "${contenido.duracionMinutos} minutos",
                            style = MaterialTheme.typography.labelLarge
                        )

                        Button(
                            onClick = {
                                onVerContenido(contenido.id)
                            },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = if (esVideo) {
                                    "Ver guía del video"
                                } else {
                                    "Ver lecciones"
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}