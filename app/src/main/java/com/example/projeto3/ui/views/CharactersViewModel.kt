package com.example.projeto3.ui.views

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projeto3.data.Character
import com.example.projeto3.network.GameOfThronesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

sealed interface CharactersUiState {
    object Loading : CharactersUiState
    data class Success(val characters: List<Character>) : CharactersUiState
    object Error : CharactersUiState
}

class CharactersViewModel : ViewModel() {

    private val _uiState: MutableStateFlow<CharactersUiState> = MutableStateFlow(CharactersUiState.Loading)
    val uiState: StateFlow<CharactersUiState> = _uiState.asStateFlow()

    init {
        getCharacters()
    }

    private fun getCharacters() {
        viewModelScope.launch {
            try {
                val characters = GameOfThronesApi.retrofitService.getCharacters()
                _uiState.value = CharactersUiState.Success(characters)
            } catch (e: IOException) {
                _uiState.value = CharactersUiState.Error
            } catch (e: HttpException) {
                _uiState.value = CharactersUiState.Error
            }
        }
    }
}