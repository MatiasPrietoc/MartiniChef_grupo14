package com.example.martinichef_grupo14.model

data class Receta(
    val id: Int,
    val nombre: String,
    val categoria: String,
    val tiempoPreparacion: String,
    val descripcion: String,
    val ingredientes: List<String>,
    val pasos: List<String>,
    val imagenResId: Int? = null,
    val esFavorita: Boolean = false
)