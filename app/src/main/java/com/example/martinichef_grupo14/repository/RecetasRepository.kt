package com.example.martinichef_grupo14.repository

import com.example.martinichef_grupo14.model.Receta

object RecetasRepository {

    private val recetas = listOf(
        Receta(
            id = 1,
            titulo = "Pasta al tomate",
            descripcion = "Una preparación sencilla para comenzar.",
            tiempoMinutos = 20,
            ingredientes = listOf(
                "200 g de pasta",
                "1 taza de salsa de tomate",
                "1 cucharada de aceite",
                "Sal a gusto"
            ),
            pasos = listOf(
                "Cocinar la pasta según las instrucciones del envase.",
                "Calentar el aceite y agregar la salsa de tomate.",
                "Escurrir la pasta y mezclarla con la salsa.",
                "Servir caliente."
            )
        ),

        Receta(
            id = 2,
            titulo = "Ensalada fresca",
            descripcion = "Una opción rápida con verduras.",
            tiempoMinutos = 10,
            ingredientes = listOf(
                "1 lechuga",
                "2 tomates",
                "1 pepino",
                "Aceite, limón y sal a gusto"
            ),
            pasos = listOf(
                "Lavar las verduras.",
                "Cortar la lechuga, los tomates y el pepino.",
                "Mezclar en un recipiente.",
                "Aliñar al momento de servir."
            )
        )
    )

    fun obtenerRecetas(): List<Receta> {
        return recetas
    }

    fun buscarPorId(id: Int): Receta? {
        return recetas.find { it.id == id }
    }
}