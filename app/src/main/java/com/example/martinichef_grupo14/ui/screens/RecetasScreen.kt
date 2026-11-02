package com.example.martinichef_grupo14.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.martinichef_grupo14.model.Receta
import com.example.martinichef_grupo14.ui.components.FotoReceta

@Composable
fun RecetasScreen(
    recetas: List<Receta>,
    onVerReceta: (Int) -> Unit,
    onVolverInicio: () -> Unit
) {
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
                    text = "Recetas de MartiniChef",
                    style = MaterialTheme.typography.headlineMedium
                )

                Spacer(Modifier.height(8.dp))

                Text(
                    text = "Encuentra tu próxima preparación.",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            items(
                items = recetas,
                key = { it.id }
            ) { receta ->
                ElevatedCard(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(20.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        FotoReceta(receta = receta)

                        Text(
                            text = receta.titulo,
                            style = MaterialTheme.typography.titleLarge
                        )

                        Text(
                            text = receta.descripcion,
                            style = MaterialTheme.typography.bodyMedium
                        )

                        Text(
                            text = "Tiempo: ${receta.tiempoMinutos} minutos",
                            style = MaterialTheme.typography.labelLarge,
                            color = MaterialTheme.colorScheme.primary
                        )

                        Button(
                            onClick = {
                                onVerReceta(receta.id)
                            },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Ver preparación")
                        }
                    }
                }
            }
        }
    }
}