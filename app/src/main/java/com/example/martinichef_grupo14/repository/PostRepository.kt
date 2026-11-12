package com.example.martinichef_grupo14.repository

import com.example.martinichef_grupo14.data.model.Post
import com.example.martinichef_grupo14.data.remote.ApiService
import com.example.martinichef_grupo14.data.remote.RetrofitInstance

class PostRepository(
    private val api: ApiService = RetrofitInstance.api
) {
    suspend fun getPosts(): List<Post> {
        return api.getPosts()
    }
}