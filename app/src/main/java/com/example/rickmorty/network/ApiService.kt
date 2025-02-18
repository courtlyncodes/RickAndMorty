package com.example.rickmorty.network

import com.example.rickmorty.model.QueryResponse
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
    @GET("character")
    suspend fun loadCharacters(): Response<QueryResponse>
}