package com.example.rickmorty.ui

import com.example.rickmorty.model.RmCharacter

sealed interface UiState {
    data class Success(val rmCharacters: List<RmCharacter>): UiState
    data object Loading: UiState
    data object Error: UiState
}