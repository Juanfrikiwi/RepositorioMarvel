package com.example.marvelcharacters.data.network.networkDataRepository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.marvelcharacters.data.local.models.CharactersEntity
import com.example.marvelcharacters.data.network.MarvelPagingSource
import com.example.marvelcharacters.data.network.MarvelService
import com.example.marvelcharacters.data.network.models.CharactersResponse
import com.example.marvelcharacters.domain.models.CharacterModel
import com.example.marvelcharacters.domain.repository.CharactersRepository
import com.example.marvelcharacters.utilities.Mappers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import javax.inject.Inject

class CharactersRepositoryImpl @Inject constructor(private val service: MarvelService) :
    CharactersRepository {
    override suspend fun getListCharacter(): Flow<PagingData<CharacterModel>> {
        return Pager(
            config = PagingConfig(enablePlaceholders = false, pageSize = NETWORK_PAGE_SIZE),
            pagingSourceFactory = {
                MarvelPagingSource(service)
            }
        ).flow
    }

    override suspend fun getCharacter(id:Int): Flow<CharacterModel> {
        val response = service.getCharacter(id)
        CharacterProvider.characters = response.data.characters
        return Mappers.mapperToListCharacters(response.data.characters).asFlow()
    }

    companion object {
        private const val NETWORK_PAGE_SIZE = 25
    }

    class CharacterProvider {
        companion object {
            var characters:List<CharactersResponse> = emptyList()
        }
    }
}