package com.example.rickmorty.data

import com.example.rickmorty.model.Character
import com.example.rickmorty.network.ApiService

/** Retrieves a list of characters from the data source */
interface RmRepository {
    suspend fun loadCharacters(): List<Character>
}

/** Network implementation of repository that retrieves characters from underlying data source. */
class DefaultRmRepository(private val rmApiService: ApiService) : RmRepository {
    override suspend fun loadCharacters(): List<Character> {
        return try {
            val response = rmApiService.loadCharacters()
            if (response.isSuccessful) {
                val chars = response.body()?.results
                // If the response is successful, but the body is null or empty, return an empty list
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