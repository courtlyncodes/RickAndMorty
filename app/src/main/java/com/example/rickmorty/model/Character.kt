package com.example.rickmorty.model

data class QueryResponse (
    val results: List<Character>
)

data class Character (
    val name: String,
    val status: String,
    val species: String,
    val gender: String,
    val image: String
)