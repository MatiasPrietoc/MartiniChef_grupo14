package com.example.martinichef_grupo14.data.remote

import com.example.martinichef_grupo14.data.model.Post
import retrofit2.http.GET

interface ApiService {
    @GET("posts")
    suspend fun getPosts(): List<Post>
}