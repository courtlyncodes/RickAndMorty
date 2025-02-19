package com.example.rickmorty.ui

import com.example.rickmorty.model.Character

sealed interface UiState {
    data class Success(val characters: List<Character>): UiState
    data object Loading: UiState
    data object Error: UiState
}