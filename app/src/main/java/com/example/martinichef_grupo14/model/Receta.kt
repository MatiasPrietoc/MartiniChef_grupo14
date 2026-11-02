package com.example.martinichef_grupo14.model

data class Receta(
    val id: Int,
    val titulo: String,
    val descripcion: String,
    val tiempoMinutos: Int,
    val ingredientes: List<String>,
    val pasos: List<String>,
    val imagenResId: Int? = null
)