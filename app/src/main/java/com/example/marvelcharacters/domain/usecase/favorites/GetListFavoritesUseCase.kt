package com.example.marvelcharacters.domain.usecase.favorites


import com.example.marvelcharacters.domain.models.CharacterModel
import com.example.marvelcharacters.domain.repository.FavoritesRepository
import javax.inject.Inject

class GetListFavoritesUseCase @Inject constructor(private val favoritesRepository: FavoritesRepository) {
    suspend fun invoke() : List<CharacterModel> {
        return favoritesRepository.getListFavoritesCharacters()
    }
}