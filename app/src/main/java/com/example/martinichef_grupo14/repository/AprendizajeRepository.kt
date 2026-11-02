package com.example.martinichef_grupo14.repository

import com.example.martinichef_grupo14.model.ContenidoAprendizaje
import com.example.martinichef_grupo14.model.Leccion
import com.example.martinichef_grupo14.model.TipoContenido

object AprendizajeRepository {

    private val contenidos = listOf(
        ContenidoAprendizaje(
            id = 1,
            titulo = "Organización de ingredientes",
            descripcion = "Aprende a preparar tu espacio antes de cocinar.",
            tipo = TipoContenido.VIDEO,
            duracionMinutos = 4,
            lecciones = listOf(
                Leccion(
                    titulo = "Lee la receta",
                    explicacion = "Revisa los ingredientes y todos los pasos " +
                            "antes de comenzar. Identifica qué utensilios usarás " +
                            "y qué preparaciones necesitan más tiempo."
                ),
                Leccion(
                    titulo = "Prepara las cantidades",
                    explicacion = "Mide los ingredientes y colócalos en " +
                            "recipientes separados. Lava y prepara las verduras " +
                            "según lo indicado en la receta."
                ),
                Leccion(
                    titulo = "Ordena tu espacio",
                    explicacion = "Deja cerca los utensilios que necesitas " +
                            "y retira los objetos que no utilizarás. Mantén una " +
                            "zona libre para apoyar los recipientes."
                )
            )
        ),

        ContenidoAprendizaje(
            id = 2,
            titulo = "Cortes básicos de verduras",
            descripcion = "Conoce tres formas de cortar para tus recetas.",
            tipo = TipoContenido.VIDEO,
            duracionMinutos = 6,
            lecciones = listOf(
                Leccion(
                    titulo = "Prepara la tabla",
                    explicacion = "Utiliza una tabla estable sobre una " +
                            "superficie firme. Lava las verduras y apóyalas " +
                            "sobre una cara plana para evitar que rueden."
                ),
                Leccion(
                    titulo = "Rodajas y bastones",
                    explicacion = "Las rodajas son cortes transversales. " +
                            "Para obtener bastones, corta primero láminas y " +
                            "después tiras. Trabaja despacio y mantén los dedos " +
                            "apartados del filo."
                ),
                Leccion(
                    titulo = "Cubos de tamaño parecido",
                    explicacion = "Agrupa algunos bastones y córtalos " +
                            "transversalmente para formar cubos. Un tamaño " +
                            "similar ayuda a que las verduras se cocinen " +
                            "de manera más uniforme."
                )
            )
        ),

        ContenidoAprendizaje(
            id = 3,
            titulo = "Primeros pasos en la cocina",
            descripcion = "Un recorrido introductorio para preparar " +
                    "tus primeras recetas.",
            tipo = TipoContenido.CURSO,
            duracionMinutos = 45,
            lecciones = listOf(
                Leccion(
                    titulo = "Organización e higiene",
                    explicacion = "Comienza lavándote las manos y limpiando " +
                            "la superficie de trabajo. Separa los utensilios " +
                            "usados con alimentos crudos de los que utilizarás " +
                            "con preparaciones listas para servir."
                ),
                Leccion(
                    titulo = "Conoce tus utensilios",
                    explicacion = "Identifica las funciones de la olla, " +
                            "la sartén, el colador y los recipientes de medida. " +
                            "Elige un tamaño adecuado para la cantidad de comida " +
                            "que vas a preparar."
                ),
                Leccion(
                    titulo = "Planifica una preparación",
                    explicacion = "Escoge una receta sencilla del catálogo. " +
                            "Anota sus ingredientes, revisa los pasos y prepara " +
                            "los utensilios. Comienza por las tareas que " +
                            "necesiten más tiempo."
                )
            )
        ),

        ContenidoAprendizaje(
            id = 4,
            titulo = "Desayunos fáciles con avena",
            descripcion = "Ideas sencillas para aprovechar la avena " +
                    "en preparaciones de desayuno.",
            tipo = TipoContenido.CURSO,
            duracionMinutos = 30,
            lecciones = listOf(
                Leccion(
                    titulo = "Prepara los ingredientes",
                    explicacion = "Reúne avena, fruta y los ingredientes " +
                            "indicados en la receta que elijas. Para una mezcla " +
                            "más fina de panqueques puedes utilizar avena molida."
                ),
                Leccion(
                    titulo = "Mezcla y ajusta la textura",
                    explicacion = "Combina primero los ingredientes húmedos " +
                            "y luego incorpora la avena. Deja reposar unos minutos " +
                            "y revisa la consistencia antes de cocinar."
                ),
                Leccion(
                    titulo = "Practica con el catálogo",
                    explicacion = "Abre la receta de panqueques de avena y " +
                            "plátano. Sigue sus cantidades y pasos, cocina " +
                            "porciones pequeñas y comprueba que el centro " +
                            "esté cocido antes de servir."
                )
            )
        )
    )

    fun obtenerContenidos(): List<ContenidoAprendizaje> {
        return contenidos
    }

    fun buscarPorId(id: Int): ContenidoAprendizaje? {
        return contenidos.find { it.id == id }
    }
}