package com.example.martinichef_grupo14.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.martinichef_grupo14.model.Receta
import com.example.martinichef_grupo14.ui.components.FotoReceta

@Composable
fun DetalleRecetaScreen(
    receta: Receta?,
    onVolver: () -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(24.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            OutlinedButton(
                onClick = onVolver
            ) {
                Text("Volver al catálogo")
            }
        }

        if (receta == null) {
            item {
                Text(
                    text = "No se encontró la receta.",
                    color = MaterialTheme.colorScheme.error
                )
            }
        } else {
            item {
                FotoReceta(
                    receta = receta
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = receta.titulo,
                    style = MaterialTheme.typography.headlineMedium
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = receta.descripcion,
                    style = MaterialTheme.typography.bodyLarge
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Tiempo: ${receta.tiempoMinutos} minutos",
                    style = MaterialTheme.typography.labelLarge
                )
            }

            item {
                HorizontalDivider()

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = "Ingredientes",
                    style = MaterialTheme.typography.titleLarge
                )
            }

            items(receta.ingredientes) { ingrediente ->
                Text(
                    text = "• $ingrediente",
                    style = MaterialTheme.typography.bodyLarge
                )
            }

            item {
                HorizontalDivider()

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = "Preparación",
                    style = MaterialTheme.typography.titleLarge
                )
            }

            itemsIndexed(receta.pasos) { indice, paso ->
                Card(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "${indice + 1}. $paso",
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }

            item {
                OutlinedButton(
                    onClick = onVolver,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Volver al catálogo")
                }
            }
        }
    }
}