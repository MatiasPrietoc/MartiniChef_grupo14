package com.example.martinichef_grupo14.viewmodel

import androidx.lifecycle.ViewModel
import com.example.martinichef_grupo14.model.Receta
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class RecetasUiState(
    val recetas: List<Receta> = emptyList(),
    val textoBusqueda: String = "",
    val recetaSeleccionada: Receta? = null
) {
    val recetasFiltradas: List<Receta>
        get() {
            if (textoBusqueda.isBlank()) {
                return recetas
            }

            return recetas.filter { receta ->
                receta.nombre.contains(textoBusqueda, ignoreCase = true) ||
                        receta.categoria.contains(textoBusqueda, ignoreCase = true)
            }
        }
}

class RecetasViewModel : ViewModel() {

    private val recetasIniciales = listOf(
        Receta(
            id = 1,
            nombre = "Pasta al pesto",
            categoria = "Pastas",
            tiempoPreparacion = "25 minutos",
            descripcion = "Pasta acompañada de una salsa de pesto casera.",
            ingredientes = listOf(
                "250 gramos de pasta",
                "Albahaca fresca",
                "1 diente de ajo",
                "Aceite de oliva",
                "Queso rallado"
            ),
            pasos = listOf(
                "Cocinar la pasta según las instrucciones del envase.",
                "Licuar la albahaca, el ajo, el aceite y el queso.",
                "Mezclar la pasta con la salsa de pesto.",
                "Servir con queso rallado."
            )
        ),
        Receta(
            id = 2,
            nombre = "Pizza Margarita",
            categoria = "Pizzas",
            tiempoPreparacion = "40 minutos",
            descripcion = "Pizza clásica con tomate, queso y albahaca.",
            ingredientes = listOf(
                "1 masa para pizza",
                "Salsa de tomate",
                "Queso mozzarella",
                "Tomate",
                "Albahaca"
            ),
            pasos = listOf(
                "Extender la masa sobre una bandeja.",
                "Agregar la salsa de tomate.",
                "Añadir el queso, el tomate y la albahaca.",
                "Hornear durante 20 minutos."
            )
        ),
        Receta(
            id = 3,
            nombre = "Ensalada César",
            categoria = "Ensaladas",
            tiempoPreparacion = "20 minutos",
            descripcion = "Ensalada fresca con pollo, lechuga y crutones.",
            ingredientes = listOf(
                "Lechuga",
                "Pechuga de pollo",
                "Crutones",
                "Queso rallado",
                "Aderezo César"
            ),
            pasos = listOf(
                "Lavar y cortar la lechuga.",
                "Cocinar y cortar la pechuga de pollo.",
                "Mezclar la lechuga, el pollo y los crutones.",
                "Agregar el aderezo y el queso."
            )
        ),
        Receta(
            id = 4,
            nombre = "Brownie de chocolate",
            categoria = "Postres",
            tiempoPreparacion = "45 minutos",
            descripcion = "Brownie de chocolate fácil y esponjoso.",
            ingredientes = listOf(
                "Chocolate",
                "Harina",
                "Azúcar",
                "Huevos",
                "Mantequilla"
            ),
            pasos = listOf(
                "Derretir el chocolate con la mantequilla.",
                "Mezclar los huevos con el azúcar.",
                "Agregar la harina y el chocolate derretido.",
                "Hornear durante 30 minutos."
            )
        )
    )

    private val _uiState = MutableStateFlow(
        RecetasUiState(recetas = recetasIniciales)
    )

    val uiState: StateFlow<RecetasUiState> = _uiState.asStateFlow()

    fun actualizarBusqueda(nuevoTexto: String) {
        _uiState.update { estadoActual ->
            estadoActual.copy(textoBusqueda = nuevoTexto)
        }
    }

    fun seleccionarReceta(id: Int) {
        _uiState.update { estadoActual ->
            estadoActual.copy(
                recetaSeleccionada = estadoActual.recetas.find { it.id == id }
            )
        }
    }

    fun limpiarSeleccion() {
        _uiState.update { estadoActual ->
            estadoActual.copy(recetaSeleccionada = null)
        }
    }

    fun alternarFavorito(id: Int) {
        _uiState.update { estadoActual ->
            val nuevasRecetas = estadoActual.recetas.map { receta ->
                if (receta.id == id) {
                    receta.copy(esFavorita = !receta.esFavorita)
                } else {
                    receta
                }
            }

            val nuevaSeleccion = estadoActual.recetaSeleccionada?.let { seleccionada ->
                nuevasRecetas.find { it.id == seleccionada.id }
            }

            estadoActual.copy(
                recetas = nuevasRecetas,
                recetaSeleccionada = nuevaSeleccion
            )
        }
    }
}