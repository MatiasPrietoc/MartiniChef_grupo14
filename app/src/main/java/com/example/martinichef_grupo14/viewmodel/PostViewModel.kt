package com.example.martinichef_grupo14.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.martinichef_grupo14.data.model.Post
import com.example.martinichef_grupo14.repository.PostRepository
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.io.IOException

sealed interface PostUiState {
    object Cargando : PostUiState
    data class Exito(val posts: List<Post>) : PostUiState
    data class Error(val mensaje: String) : PostUiState
}

class PostViewModel(
    private val repository: PostRepository = PostRepository()
) : ViewModel() {

    private val _uiState = MutableStateFlow<PostUiState>(PostUiState.Cargando)
    val uiState = _uiState.asStateFlow()

    private var solicitud: Job? = null

    init {
        cargarPosts()
    }

    fun cargarPosts() {
        if (solicitud?.isActive == true) return

        solicitud = viewModelScope.launch {
            _uiState.value = PostUiState.Cargando

            try {
                val posts = repository.getPosts()
                _uiState.value = PostUiState.Exito(posts)
            } catch (error: CancellationException) {
                throw error
            } catch (error: IOException) {
                _uiState.value = PostUiState.Error(
                    "No se pudo conectar. Revisa tu conexión e inténtalo otra vez."
                )
            } catch (error: Exception) {
                _uiState.value = PostUiState.Error(
                    "No se pudieron cargar las publicaciones."
                )
            }
        }
    }
}