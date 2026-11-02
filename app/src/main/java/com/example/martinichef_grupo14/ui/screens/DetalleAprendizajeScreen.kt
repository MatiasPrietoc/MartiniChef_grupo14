package com.example.martinichef_grupo14.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.martinichef_grupo14.model.ContenidoAprendizaje
import com.example.martinichef_grupo14.model.TipoContenido

@Composable
fun DetalleAprendizajeScreen(
    contenido: ContenidoAprendizaje?,
    onVolver: () -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            OutlinedButton(
                onClick = onVolver
            ) {
                Text("Volver a Aprender")
            }
        }

        if (contenido == null) {
            item {
                Text(
                    text = "No se encontró el contenido.",
                    color = MaterialTheme.colorScheme.error
                )
            }
        } else {
            item {
                Text(
                    text = if (contenido.tipo == TipoContenido.VIDEO) {
                        "Guía de video simulado"
                    } else {
                        "Curso simulado"
                    },
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.primary
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = contenido.titulo,
                    style = MaterialTheme.typography.headlineMedium
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = contenido.descripcion,
                    style = MaterialTheme.typography.bodyLarge
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Duración estimada: " +
                            "${contenido.duracionMinutos} minutos",
                    style = MaterialTheme.typography.labelLarge
                )
            }

            item {
                Card(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = if (contenido.tipo == TipoContenido.VIDEO) {
                            "Esta ficha presenta las ideas de un video " +
                                    "simulado mediante una guía de lectura."
                        } else {
                            "Recorre las lecciones y practica lo aprendido " +
                                    "con las recetas del catálogo."
                        },
                        modifier = Modifier.padding(16.dp),
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }

            item {
                HorizontalDivider()

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = "Contenido de aprendizaje",
                    style = MaterialTheme.typography.titleLarge
                )
            }

            itemsIndexed(contenido.lecciones) { indice, leccion ->
                Card(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(20.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Text(
                            text = "${indice + 1}. ${leccion.titulo}",
                            style = MaterialTheme.typography.titleMedium
                        )

                        Text(
                            text = leccion.explicacion,
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                }
            }

            item {
                OutlinedButton(
                    onClick = onVolver,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Volver a Aprender")
                }
            }
        }
    }
}