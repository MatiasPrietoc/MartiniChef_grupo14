package com.example.martinichef_grupo14.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.martinichef_grupo14.model.Receta
import com.example.martinichef_grupo14.viewmodel.RecetasViewModel

@Composable
fun RecetasScreen(
    onRecetaClick: (Int) -> Unit = {},
    onVolver: () -> Unit = {},
    viewModel: RecetasViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onVolver) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Volver"
                )
            }

            Text(
                text = "Recetas MartiniChef",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = uiState.textoBusqueda,
            onValueChange = viewModel::actualizarBusqueda,
            modifier = Modifier.fillMaxWidth(),
            label = {
                Text("Buscar recetas")
            },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Buscar"
                )
            },
            singleLine = true
        )

        Spacer(modifier = Modifier.height(16.dp))

        if (uiState.recetasFiltradas.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                contentAlignment = Alignment.Center
            ) {
                Text("No se encontraron recetas")
            }
        } else {
            LazyColumn(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(
                    items = uiState.recetasFiltradas,
                    key = { receta -> receta.id }
                ) { receta ->
                    TarjetaReceta(
                        receta = receta,
                        onFavoritoClick = {
                            viewModel.alternarFavorito(receta.id)
                        },
                        onVerRecetaClick = {
                            viewModel.seleccionarReceta(receta.id)
                            onRecetaClick(receta.id)
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun TarjetaReceta(
    receta: Receta,
    onFavoritoClick: () -> Unit,
    onVerRecetaClick: () -> Unit
) {
    val colorCorazon by animateColorAsState(
        targetValue = if (receta.esFavorita) {
            Color(0xFFD32F2F)
        } else {
            MaterialTheme.colorScheme.onSurfaceVariant
        },
        label = "colorCorazon"
    )

    val escalaCorazon by animateFloatAsState(
        targetValue = if (receta.esFavorita) 1.2f else 1f,
        label = "escalaCorazon"
    )

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.Top
            ) {
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = receta.nombre,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        text = "${receta.categoria} • ${receta.tiempoPreparacion}",
                        color = MaterialTheme.colorScheme.primary
                    )
                }

                IconButton(
                    onClick = onFavoritoClick,
                    modifier = Modifier.graphicsLayer(
                        scaleX = escalaCorazon,
                        scaleY = escalaCorazon
                    )
                ) {
                    Icon(
                        imageVector = if (receta.esFavorita) {
                            Icons.Filled.Favorite
                        } else {
                            Icons.Outlined.FavoriteBorder
                        },
                        contentDescription = if (receta.esFavorita) {
                            "Quitar de favoritos"
                        } else {
                            "Agregar a favoritos"
                        },
                        tint = colorCorazon
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(text = receta.descripcion)

            AnimatedVisibility(visible = receta.esFavorita) {
                Text(
                    text = "Guardada en favoritos",
                    modifier = Modifier.padding(top = 8.dp),
                    color = Color(0xFFD32F2F),
                    fontWeight = FontWeight.SemiBold
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Button(
                onClick = onVerRecetaClick,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Ver receta")
            }
        }
    }
}