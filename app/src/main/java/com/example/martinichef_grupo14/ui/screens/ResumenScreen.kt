package com.example.martinichef_grupo14.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.martinichef_grupo14.viewmodel.UsuarioViewModel

@Composable
fun ResumenScreen(
    modifier: Modifier = Modifier,
    navController: NavController? = null,
    viewModel: UsuarioViewModel
) {
    val uiState by viewModel.uiState.collectAsState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Resumen del Registro",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(24.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Column(
                modifier = Modifier.padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    text = "Nombre:",
                    style = MaterialTheme.typography.labelLarge
                )
                Text(
                    text = uiState.nombre.ifBlank { "No especificado" },
                    style = MaterialTheme.typography.bodyLarge
                )

                HorizontalDivider()

                Text(
                    text = "Correo Electrónico:",
                    style = MaterialTheme.typography.labelLarge
                )
                Text(
                    text = uiState.email.ifBlank { "No especificado" },
                    style = MaterialTheme.typography.bodyLarge
                )

                HorizontalDivider()
                Text(
                    text = "Dirección:",
                    style = MaterialTheme.typography.labelLarge
                )

                Text(
                    text = uiState.direccion.ifBlank { "No especificada" },
                    style = MaterialTheme.typography.bodyLarge
                )

                HorizontalDivider()
                Text(
                    text = "Estado del registro:",
                    style = MaterialTheme.typography.labelLarge
                )
                Text(
                    text = if (uiState.esValido) "Validado con éxito" else "Incompleto / No validado",
                    color = if (uiState.esValido) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = { navController?.navigate("pantalla_principal") },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            Text("Volver al Inicio")
        }
    }
}