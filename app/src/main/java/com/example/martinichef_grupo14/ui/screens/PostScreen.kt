package com.example.martinichef_grupo14.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.martinichef_grupo14.viewmodel.PostUiState

@Composable
fun PostScreen(
    estado: PostUiState,
    onReintentar: () -> Unit,
    onVolver: () -> Unit
) {
    Column(modifier = Modifier.fillMaxSize()) {
        TextButton(
            onClick = onVolver,
            modifier = Modifier.padding(horizontal = 16.dp)
        ) {
            Text("← Volver a Aprender")
        }

        Text(
            text = "Publicaciones de práctica",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(horizontal = 24.dp)
        )

        Text(
            text = "Datos de ejemplo de JSONPlaceholder",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(
                start = 24.dp,
                end = 24.dp,
                top = 4.dp,
                bottom = 16.dp
            )
        )

        when (estado) {
            PostUiState.Cargando -> {
                Box(
                    modifier = Modifier.weight(1f).fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        CircularProgressIndicator()
                        Text("Cargando publicaciones…")
                    }
                }
            }

            is PostUiState.Error -> {
                Column(
                    modifier = Modifier.fillMaxWidth().padding(24.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        text = estado.mensaje,
                        color = MaterialTheme.colorScheme.error
                    )
                    Button(onClick = onReintentar) {
                        Text("Reintentar")
                    }
                }
            }

            is PostUiState.Exito -> {
                if (estado.posts.isEmpty()) {
                    Text(
                        text = "No hay publicaciones disponibles.",
                        modifier = Modifier.padding(24.dp)
                    )
                } else {
                    LazyColumn(
                        modifier = Modifier.weight(1f).fillMaxWidth(),
                        contentPadding = PaddingValues(24.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(estado.posts, key = { it.id }) { post ->
                            Card(modifier = Modifier.fillMaxWidth()) {
                                Column(
                                    modifier = Modifier.padding(16.dp),
                                    verticalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    Text(
                                        text = post.title,
                                        style = MaterialTheme.typography.titleMedium
                                    )
                                    Text(text = post.body)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}