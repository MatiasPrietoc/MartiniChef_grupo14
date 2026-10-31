package com.example.martinichef_grupo14.ui.screens

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.martinichef_grupo14.viewmodel.RecetasViewModel

@Composable
fun DetalleRecetaScreen(
    viewModel: RecetasViewModel,
    onVolver: () -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsState()
    val receta = uiState.recetaSeleccionada

    if (receta == null) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "No se encontró la receta",
                    style = MaterialTheme.typography.titleLarge
                )

                Spacer(modifier = Modifier.height(16.dp))

                Button(onClick = onVolver) {
                    Text("Volver al catálogo")
                }
            }
        }

        return
    }

    val colorFavorito by animateColorAsState(
        targetValue = if (receta.esFavorita) {
            Color(0xFFD32F2F)
        } else {
            MaterialTheme.colorScheme.onSurfaceVariant
        },
        label = "colorFavoritoDetalle"
    )

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onVolver) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Volver al catálogo"
                    )
                }

                Text(
                    text = "Detalle de la receta",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        item {
            Text(
                text = receta.nombre,
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )
        }

        item {
            Text(
                text = "${receta.categoria} • ${receta.tiempoPreparacion}",
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.titleMedium
            )
        }

        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                )
            ) {
                Text(
                    text = receta.descripcion,
                    modifier = Modifier.padding(16.dp),
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }

        item {
            Text(
                text = "Ingredientes",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
        }

        itemsIndexed(receta.ingredientes) { indice, ingrediente ->
            Text(
                text = "${indice + 1}. $ingrediente",
                style = MaterialTheme.typography.bodyLarge
            )
        }

        item {
            HorizontalDivider()
        }

        item {
            Text(
                text = "Preparación",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
        }

        itemsIndexed(receta.pasos) { indice, paso ->
            Card(
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.Top
                ) {
                    Text(
                        text = "${indice + 1}.",
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    Text(
                        text = paso,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }
        }

        item {
            Button(
                onClick = {
                    viewModel.alternarFavorito(receta.id)
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(
                    imageVector = if (receta.esFavorita) {
                        Icons.Filled.Favorite
                    } else {
                        Icons.Outlined.FavoriteBorder
                    },
                    contentDescription = null,
                    tint = colorFavorito
                )

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = if (receta.esFavorita) {
                        "Quitar de favoritos"
                    } else {
                        "Agregar a favoritos"
                    }
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