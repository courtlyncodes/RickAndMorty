package com.example.rickmorty.data

import com.example.rickmorty.model.Character
import com.example.rickmorty.network.ApiService

interface RmRepository {
    suspend fun loadCharacters(): List<Character>
}

class DefaultRmRepository(private val rmApiService: ApiService) : RmRepository {
    // Retrieves a list of characters from the data source
    override suspend fun loadCharacters(): List<Character> {
        return try {
            val response = rmApiService.loadCharacters()
            if (response.isSuccessful) {
                val chars = response.body()?.results
                if (chars.isNullOrEmpty()) {
                    emptyList()
                } else {
                    chars
                }
            } else {
                throw Exception("Error loading data: ${response.code()}")
            }
        } catch (e: Exception) {
            throw Exception("Error: $e")
        }
    }
}