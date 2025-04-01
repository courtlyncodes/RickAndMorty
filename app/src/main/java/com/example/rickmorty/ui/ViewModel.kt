package com.example.rickmorty.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.rickmorty.RmApplication
import com.example.rickmorty.data.RmRepository
import com.example.rickmorty.model.RmCharacter
import kotlinx.coroutines.launch
import okio.IOException
import retrofit2.HttpException

class ViewModel(private val repository: RmRepository) : ViewModel() {

    var uiState: UiState by mutableStateOf(UiState.Loading)
        private set

    private var _chars = emptyList<RmCharacter>()

    init {
        loadChars()
    }

    private fun loadChars() {
        viewModelScope.launch {
            uiState = UiState.Loading
            uiState = try {
                _chars = repository.loadCharacters()
                if (_chars.isEmpty()) {
                    UiState.Error
                } else {
                    UiState.Success(_chars)
                }
            } catch (e: IOException) {
                UiState.Error
            } catch (e: HttpException) {
                UiState.Error
            }
        }
    }


    fun searchChars(name: String) {
        viewModelScope.launch {
            val filteredChars = _chars.filter { it.name.contains(name, ignoreCase = true) }
            uiState =
                UiState.Success(filteredChars)
            }
        }


    // Factory for CoinsViewModel that takes CoinsRepository as a dependency
    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application =
                    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as RmApplication)
                val repository = application.container.repository
                ViewModel(repository = repository)
            }
        }
    }
}