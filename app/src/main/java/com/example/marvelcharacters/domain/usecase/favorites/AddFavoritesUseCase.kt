package com.example.marvelcharacters.domain.usecase.favorites

import com.example.marvelcharacters.domain.models.CharacterModel
import com.example.marvelcharacters.domain.repository.FavoritesRepository
import javax.inject.Inject

class AddFavoritesUseCase @Inject constructor(private val favoritesRepository: FavoritesRepository) {
    suspend fun invoke(character : CharacterModel){
        favoritesRepository.insertFavoriteCharacter(character)
    }
}