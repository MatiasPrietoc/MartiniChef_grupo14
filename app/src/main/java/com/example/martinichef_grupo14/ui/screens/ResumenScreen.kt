package com.example.martinichef_grupo14.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.martinichef_grupo14.viewmodel.UsuarioViewModel

@Composable
fun ResumenScreen(
    navController: NavController,
    viewModel: UsuarioViewModel
) {
    val estado by viewModel.estado.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = "Resumen del Registro",
            style = MaterialTheme.typography.headlineMedium
        )

        Card(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(text = "Nombre: ${estado.nombre}")
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "Correo: ${estado.correo}")
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "Dirección: ${estado.direccion}")
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "Contraseña: ${"*".repeat(estado.clave.length)}")
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Términos: ${if (estado.aceptaTerminos) "Aceptados" else "No aceptados"}"
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { navController.popBackStack() },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Volver al Registro")
        }
    }
}