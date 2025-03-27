package com.example.rickmorty.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

data class QueryResponse (
    val results: List<RmCharacter>
)

@Parcelize
@Entity(tableName = "rm_characters")
data class RmCharacter (
    @PrimaryKey val id: Int,
    val name: String,
    val status: String,
    val species: String,
    val gender: String,
    val image: String,
): Parcelable