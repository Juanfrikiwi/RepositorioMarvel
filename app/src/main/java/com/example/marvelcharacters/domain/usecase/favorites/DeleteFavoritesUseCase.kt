package com.example.marvelcharacters.domain.usecase.favorites

import com.example.marvelcharacters.domain.models.CharacterModel
import com.example.marvelcharacters.domain.repository.FavoritesRepository
import javax.inject.Inject

class DeleteFavoritesUseCase @Inject constructor(private val favoritesRepository: FavoritesRepository) {
    suspend fun invoke(character : CharacterModel):Boolean{
        return try {
            favoritesRepository.deleteFavoriteCharacter(character)
            true
        } catch (e: Exception) {
            false
        }
    }
}