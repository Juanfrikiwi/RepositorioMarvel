package com.example.marvelcharacters.domain.repository


import com.example.marvelcharacters.domain.models.CharacterModel
import kotlinx.coroutines.flow.Flow

interface FavoritesRepository {
    suspend fun getListFavoritesCharacters() : List<CharacterModel>

    suspend fun isExistId(characterId: Int) : Flow<Boolean>

    suspend fun getFavoriteCharacter(characterId: Int): CharacterModel

    suspend fun insertFavoriteCharacter(character: CharacterModel)

    suspend fun deleteFavoriteCharacter(character: CharacterModel)

    suspend fun deleteAllFavoriteCharacter()

    suspend fun insertAll(characters: List<CharacterModel>)

}