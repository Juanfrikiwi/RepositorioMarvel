package com.example.marvelcharacters.ui.detail

import androidx.lifecycle.*
import com.example.marvelcharacters.data.local.models.CharactersEntity
import com.example.marvelcharacters.domain.models.CharacterModel
import com.example.marvelcharacters.domain.usecase.favorites.GetFavoriteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailFavoritesViewModel @Inject constructor(
    private val getFavoriteUseCase: GetFavoriteUseCase
) : ViewModel() {
    val successResponse = MutableLiveData<CharacterModel>()
    val errorResponse = MutableLiveData<Throwable>()
    fun getFavorite(characterId: Int) {
        viewModelScope.launch {
            try {
                successResponse.postValue(
                    getFavoriteUseCase.invoke(characterId)
                )
            } catch (e: Exception) {
                errorResponse.postValue(e)
            }
        }
    }
}