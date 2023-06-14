package com.example.projeto3.ui.views

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projeto3.data.Character
import com.example.projeto3.network.RickAndMortyApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

sealed interface CharactersUiState {
    object Loading: CharactersUiState

    data class Success(val characters: List<Character>): CharactersUiState

    object Error: CharactersUiState
}

class CharactersViewModel: ViewModel() {

    private var _uiState: MutableStateFlow<CharactersUiState> = MutableStateFlow(CharactersUiState.Loading)
    val uiState: StateFlow<CharactersUiState> = _uiState.asStateFlow()

    init {
        getCharacters()
    }

    private fun getCharacters(){
        viewModelScope.launch {
            try {
                _uiState.value = CharactersUiState.Success(RickAndMortyApi.retrofitService.getCharacters())
            }catch (e: IOException){
                _uiState.value = CharactersUiState.Error
            }catch (e: HttpException){
                _uiState.value = CharactersUiState.Error
            }
        }
    }
}