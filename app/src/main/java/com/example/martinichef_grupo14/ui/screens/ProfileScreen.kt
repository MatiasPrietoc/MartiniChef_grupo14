package com.example.martinichef_grupo14.ui.screens

import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.martinichef_grupo14.ui.components.ImagenInteligente
import com.example.martinichef_grupo14.viewmodel.PerfilViewModel
import java.io.File

@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    viewModel: PerfilViewModel = viewModel()
) {
    val context = LocalContext.current
    val imagenUri by viewModel.imagenUri.collectAsState()

    var tempCamaraUri by remember { mutableStateOf<Uri?>(null) }

    // Función auxiliar para generar la URI temporal
    fun crearTempUri(): Uri {
        val file = File(context.cacheDir, "foto_temp_${System.currentTimeMillis()}.jpg")
        return FileProvider.getUriForFile(
            context,
            "${context.packageName}.provider",
            file
        )
    }

    // Launcher para la Galería
    val galeriaLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let { viewModel.setImagenUri(it) }
    }

    // Launcher para tomar la Foto
    val camaraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture()
    ) { exito: Boolean ->
        if (exito && tempCamaraUri != null) {
            viewModel.setImagenUri(tempCamaraUri)
        }
    }

    // Launcher para solicitar el Permiso de Cámara
    val permisoCamaraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { concedido: Boolean ->
        if (concedido) {
            val uri = crearTempUri()
            tempCamaraUri = uri
            camaraLauncher.launch(uri)
        } else {
            Toast.makeText(context, "Se requiere permiso de cámara", Toast.LENGTH_SHORT).show()
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        ImagenInteligente(imagenUri = imagenUri)

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = { galeriaLauncher.launch("image/*") },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Abrir Galería")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                val estadoPermiso = ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.CAMERA
                )
                if (estadoPermiso == PackageManager.PERMISSION_GRANTED) {
                    val uri = crearTempUri()
                    tempCamaraUri = uri
                    camaraLauncher.launch(uri)
                } else {
                    permisoCamaraLauncher.launch(Manifest.permission.CAMERA)
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Tomar Foto")
        }
    }
}