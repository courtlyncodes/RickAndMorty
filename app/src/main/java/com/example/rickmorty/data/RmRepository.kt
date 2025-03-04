package com.example.rickmorty.data

import com.example.rickmorty.model.RmCharacter
import com.example.rickmorty.network.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/** Retrieves a list of characters from the data source */
interface RmRepository {
    suspend fun loadCharacters(): List<RmCharacter>
}

/** Network implementation of repository that retrieves characters from underlying data source. */
class DefaultRmRepository(
    private val rmApiService: ApiService,
    private val characterDao: CharacterDao
) : RmRepository {
    override suspend fun loadCharacters(): List<RmCharacter> {
        return withContext(Dispatchers.IO) {
            val cachedCharacters = characterDao.getAllCharacters()
            (cachedCharacters.ifEmpty {
                val response = rmApiService.loadCharacters()
                val entities = response.body()!!.results.map {
                    RmCharacter(
                        id = it.id,
                        name = it.name,
                        status = it.status,
                        species = it.species,
                        gender = it.gender,
                        image = it.image
                    )
                }
                characterDao.insertAll(entities)
                entities
            })
        }
    }
}
