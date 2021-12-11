package com.example.marvelcharacters.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.marvelcharacters.domain.models.CharacterModel
import com.example.marvelcharacters.domain.usecase.characters.GetListCharactersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getListCharactersUseCase: GetListCharactersUseCase
) : ViewModel() {
    val successResponse = MutableLiveData<PagingData<CharacterModel>>()
    val errorResponse = MutableLiveData<Throwable>()
    val onStart = MutableLiveData<Boolean>()

    fun getListCharacters() {
        viewModelScope.launch {
            getListCharactersUseCase.invoke().cachedIn(viewModelScope)
                .onStart {
                    onStart.postValue(true)
                }
                .catch { exception ->
                    errorResponse.postValue(exception)
                }
                .collect { result ->
                    successResponse.postValue(result)
                }
        }
    }
}