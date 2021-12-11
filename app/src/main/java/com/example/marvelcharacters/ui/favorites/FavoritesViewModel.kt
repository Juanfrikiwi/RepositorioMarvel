package com.example.marvelcharacters.ui.favorites

import androidx.lifecycle.*
import com.example.marvelcharacters.domain.models.CharacterModel
import com.example.marvelcharacters.domain.usecase.favorites.DeleteFavoritesUseCase
import com.example.marvelcharacters.domain.usecase.favorites.GetListFavoritesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val getListFavoritesUseCase: GetListFavoritesUseCase,
    private val deleteFavoritesUseCase: DeleteFavoritesUseCase
) : ViewModel() {
    val deleteResponse = MutableLiveData<Boolean>()
    val successResponse = MutableLiveData<List<CharacterModel>>()
    val errorResponse = MutableLiveData<Throwable>()
    
    fun getListFavorites() {
        viewModelScope.launch {
            try {
                successResponse.postValue(
                    getListFavoritesUseCase.invoke()
                )
            } catch (e: Exception) {
                errorResponse.postValue(e)
            }
        }
    }

    fun deleteCharacter(character: CharacterModel) {
        viewModelScope.launch {
            deleteResponse.postValue(deleteFavoritesUseCase.invoke(character))
        }
    }
}