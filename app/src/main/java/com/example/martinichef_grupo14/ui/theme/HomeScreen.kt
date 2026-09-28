package com.example.martinichef_grupo14.ui.theme

import kotlin.OptIn
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.martinichef_grupo14.R

// Componente principal de la pantalla base utilizando Jetpack Compose
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen() {
    // Scaffold provee la estructura visual básica (barra superior, contenido, etc.)
    Scaffold(
        topBar = {
            TopAppBar(title = { Text("M1 App Kotlin") })
        }
    ) { innerPadding ->
        // Column organiza los elementos visuales de forma vertical
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp), // Margen interno alrededor de toda la columna
            verticalArrangement = Arrangement.spacedBy(20.dp) // Espaciado uniforme entre componentes
        ) {
            // Texto de bienvenida
            Text(text = "Bienvenido!")

            // Botón interactivo de prueba
            Button(onClick = { /* Acción futura */ }) {
                Text("Soy un boton")
            }

            // Muestra del recurso de imagen cargado en la carpeta drawable
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Logo App",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp),
                contentScale = ContentScale.Fit
            )
        }
    }
}

// Vista previa para visualizar la pantalla en el editor sin necesidad de ejecutar el emulador
@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen()
}