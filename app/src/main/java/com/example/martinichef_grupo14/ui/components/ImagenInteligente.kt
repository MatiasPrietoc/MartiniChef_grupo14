package com.example.martinichef_grupo14.ui.components

import android.net.Uri
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

@Composable
fun ImagenInteligente(
    imagenUri: Uri?,
    modifier: Modifier = Modifier
) {
    if (imagenUri != null) {
        AsyncImage(
            model = imagenUri,
            contentDescription = "Foto de perfil",
            modifier = modifier
                .size(160.dp)
                .clip(CircleShape)
                .border(3.dp, MaterialTheme.colorScheme.primary, CircleShape),
            contentScale = ContentScale.Crop
        )
    } else {
        Icon(
            imageVector = Icons.Default.AccountCircle,
            contentDescription = "Icono por defecto",
            modifier = modifier.size(160.dp),
            tint = MaterialTheme.colorScheme.primary
        )
    }
}