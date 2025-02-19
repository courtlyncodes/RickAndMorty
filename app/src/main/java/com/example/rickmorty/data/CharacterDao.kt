package com.example.rickmorty.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.rickmorty.model.Character

@Dao
interface CharacterDao {
    @Query("SELECT * FROM characters")
    suspend fun getAllCharacters(): List<Character>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(characters: List<Character>)
}