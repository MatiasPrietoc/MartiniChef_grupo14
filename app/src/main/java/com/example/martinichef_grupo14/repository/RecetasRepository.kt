package com.example.martinichef_grupo14.repository

import com.example.martinichef_grupo14.R
import com.example.martinichef_grupo14.model.Receta

object RecetasRepository {

    private val recetas = listOf(
        Receta(
            id = 1,
            titulo = "Pasta al tomate",
            descripcion = "Una preparación sencilla para comenzar.",
            tiempoMinutos = 20,
            imagenResId = R.drawable.receta_pasta_tomate,
            ingredientes = listOf(
                "200 g de pasta",
                "1 taza de salsa de tomate",
                "1 cucharada de aceite de oliva",
                "Sal a gusto",
                "Albahaca para decorar, opcional"
            ),
            pasos = listOf(
                "Cocinar la pasta según las instrucciones del envase.",
                "Calentar el aceite en una sartén.",
                "Agregar la salsa de tomate y calentar a fuego bajo.",
                "Escurrir la pasta y mezclarla con la salsa.",
                "Servir y decorar con albahaca si se desea."
            )
        ),

        Receta(
            id = 2,
            titulo = "Ensalada fresca",
            descripcion = "Una opción rápida con verduras.",
            tiempoMinutos = 10,
            imagenResId = R.drawable.receta_ensalada_fresca,
            ingredientes = listOf(
                "1 lechuga pequeña",
                "2 tomates",
                "1 pepino",
                "1 cucharada de aceite de oliva",
                "Jugo de limón y sal a gusto"
            ),
            pasos = listOf(
                "Lavar las verduras.",
                "Cortar la lechuga, los tomates y el pepino.",
                "Mezclar las verduras en un recipiente.",
                "Aliñar con aceite, limón y sal al servir."
            )
        ),

        Receta(
            id = 3,
            titulo = "Crema de zapallo y zanahoria",
            descripcion = "Una crema suave y colorida para días frescos.",
            tiempoMinutos = 35,
            imagenResId = R.drawable.receta_crema_zapallo,
            ingredientes = listOf(
                "500 g de zapallo pelado",
                "2 zanahorias",
                "1/2 cebolla",
                "1 cucharada de aceite de oliva",
                "700 ml de agua o caldo de verduras",
                "Sal y pimienta a gusto",
                "Perejil para decorar, opcional"
            ),
            pasos = listOf(
                "Lavar y cortar el zapallo y las zanahorias en cubos.",
                "Picar la cebolla y cocinarla con el aceite en una olla.",
                "Agregar el zapallo, las zanahorias y el caldo.",
                "Cocinar hasta que las verduras estén blandas.",
                "Retirar del fuego y dejar bajar la temperatura.",
                "Triturar con una batidora de inmersión hasta obtener una crema.",
                "Ajustar la textura con más agua si es necesario.",
                "Sazonar, calentar suavemente y servir."
            )
        ),

        Receta(
            id = 4,
            titulo = "Panqueques de avena y plátano",
            descripcion = "Un desayuno sencillo con avena y fruta.",
            tiempoMinutos = 20,
            imagenResId = R.drawable.receta_panqueques_avena,
            ingredientes = listOf(
                "1 plátano maduro",
                "1 huevo",
                "1/2 taza de avena",
                "1/4 taza de leche",
                "1/2 cucharadita de polvos de hornear",
                "Aceite para la sartén",
                "Rodajas de plátano para servir",
                "Miel para acompañar, opcional"
            ),
            pasos = listOf(
                "Moler el plátano en un recipiente.",
                "Agregar el huevo y la leche y mezclar.",
                "Incorporar la avena molida y los polvos de hornear.",
                "Dejar reposar la mezcla durante cinco minutos.",
                "Calentar una sartén antiadherente ligeramente aceitada.",
                "Formar panqueques pequeños y cocinar a fuego medio-bajo.",
                "Voltear cuando aparezcan burbujas y la base esté firme.",
                "Cocinar el otro lado hasta que el centro esté cocido.",
                "Servir con rodajas de plátano y miel si se desea."
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