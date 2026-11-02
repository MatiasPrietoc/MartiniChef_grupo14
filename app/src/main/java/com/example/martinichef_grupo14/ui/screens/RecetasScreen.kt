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
    onVerReceta: (Int) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Text(
                text = "Recetas de MartiniChef",
                style = MaterialTheme.typography.headlineMedium
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Catálogo de demostración",
                style = MaterialTheme.typography.bodyLarge
            )
        }

        items(
            items = recetas,
            key = { receta -> receta.id }
        ) { receta ->
            Card(
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
                        style = MaterialTheme.typography.labelLarge
                    )

                    Button(
                        onClick = { onVerReceta(receta.id) }
                    ) {
                        Text("Ver preparación")
                    }
                }
            }
        }
    }
}