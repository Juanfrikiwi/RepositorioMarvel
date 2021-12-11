package com.example.marvelcharacters.domain.usecase.favorites

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.example.marvelcharacters.domain.repository.FavoritesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class IsFavoritesUseCase @Inject constructor(private val favoritesRepository: FavoritesRepository) {
    suspend fun invoke(id:Int) : LiveData<Boolean> {
        return favoritesRepository.isExistId(id).asLiveData()
    }
}