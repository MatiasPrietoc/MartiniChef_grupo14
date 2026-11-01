package com.example.martinichef_grupo14.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.material.icons.filled.Restaurant

@Composable
fun BarraNavegacion(
    rutaActual: String?,
    onNavegar: (String) -> Unit
) {
    NavigationBar {

        NavigationBarItem(
            selected = rutaActual == "pantalla_principal",
            onClick = { onNavegar("pantalla_principal") },
            icon = {
                Icon(
                    imageVector = Icons.Default.Home,
                    contentDescription = null
                )
            },
            label = { Text("Inicio") }
        )
        NavigationBarItem(
            selected = rutaActual == "recetas",
            onClick = { onNavegar("recetas") },
            icon = {
                Icon(
                    imageVector = Icons.Default.Restaurant,
                    contentDescription = null
                )
            },
            label = { Text("Recetas") }
        )

        NavigationBarItem(
            selected = rutaActual == "perfil",
            onClick = { onNavegar("perfil") },
            icon = {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = null
                )
            },
            label = { Text("Perfil") }
        )

        NavigationBarItem(
            selected = rutaActual == "settings",
            onClick = { onNavegar("settings") },
            icon = {
                Icon(
                    imageVector = Icons.Default.Settings,
                    contentDescription = null
                )
            },
            label = { Text("Ajustes") }
        )
    }
}
