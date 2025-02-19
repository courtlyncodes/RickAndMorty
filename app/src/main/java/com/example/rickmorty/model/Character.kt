package com.example.rickmorty.model

import androidx.room.Entity
import androidx.room.PrimaryKey

data class QueryResponse (
    val results: List<Character>
)

@Entity(tableName = "characters")
data class Character (
    @PrimaryKey val id: Int,
    val name: String,
    val status: String,
    val species: String,
    val gender: String,
    val image: String
)