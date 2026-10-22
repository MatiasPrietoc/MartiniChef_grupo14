package com.example.martinichef_grupo14.viewmodel

import android.net.Uri
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class PerfilViewModel : ViewModel() {

    private val _imagenUri = MutableStateFlow<Uri?>(null)
    val imagenUri: StateFlow<Uri?> = _imagenUri

    fun setImagenUri(uri: Uri?) {
        _imagenUri.value = uri
    }
}