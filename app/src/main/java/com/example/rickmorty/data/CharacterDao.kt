package com.example.rickmorty.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.rickmorty.model.RmCharacter

@Dao
interface CharacterDao {
    @Query("SELECT * FROM rm_characters")
    suspend fun getAllCharacters(): List<RmCharacter>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(rmCharacters: List<RmCharacter>)
}