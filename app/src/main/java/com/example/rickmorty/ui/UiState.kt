package com.example.rickmorty.ui

sealed interface UiState {
    data class Success(val characters: List<Character>) : UiState)
}