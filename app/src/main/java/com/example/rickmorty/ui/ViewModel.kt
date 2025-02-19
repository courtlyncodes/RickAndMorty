package com.example.rickmorty.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.example.rickmorty.data.RmRepository
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.launch
import okio.IOException
import retrofit2.HttpException

class ViewModel(private val repository: RmRepository): ViewModel() {
    var uiState: UiState by mutableStateOf(UiState.Loading)
        private set

    init {
        loadChars()
    }

    private fun loadChars() {
        viewModelScope.launch {
            uiState = UiState.Loading
            uiState = try {
                val chars = repository.loadCharacters()
                if (chars.isEmpty()) {
                    UiState.Error
                } else {
                    UiState.Success(chars)
                }
            } catch (e: IOException) {
                UiState.Error
            } catch (e: HttpException) {
                UiState.Error
            }
        }
    }
}