package com.example.marvelcharacters.domain.repository


import androidx.paging.PagingData
import com.example.marvelcharacters.domain.models.CharacterModel
import kotlinx.coroutines.flow.Flow

interface CharactersRepository {
    suspend fun getListCharacter() : Flow<PagingData<CharacterModel>>
    suspend fun getCharacter(id:Int) : Flow<CharacterModel>

}