package com.example.martinichef_grupo14.model

enum class TipoContenido {
    VIDEO,
    CURSO
}

data class Leccion(
    val titulo: String,
    val explicacion: String
)

data class ContenidoAprendizaje(
    val id: Int,
    val titulo: String,
    val descripcion: String,
    val tipo: TipoContenido,
    val duracionMinutos: Int,
    val lecciones: List<Leccion>
)