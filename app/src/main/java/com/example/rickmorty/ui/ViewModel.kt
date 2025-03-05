package com.example.rickmorty.ui

import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import com.example.rickmorty.data.RmRepository
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.palette.graphics.Palette
import com.example.rickmorty.RmApplication
import com.google.ar.core.Config
import kotlinx.coroutines.launch
import okio.IOException
import retrofit2.HttpException
import android.graphics.Bitmap
import java.util.*

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

    fun calcDominantColor(drawable: Drawable, onFinish: (Color) -> Unit) {
        val bmp = (drawable as BitmapDrawable).bitmap.copy(Bitmap.Config.ARGB_8888, true)

        Palette.from(bmp).generate { palette ->
            palette?.dominantSwatch?.rgb?.let { colorValue ->
                onFinish(Color(colorValue))
            }
        }
    }

    // Factory for CoinsViewModel that takes CoinsRepository as a dependency
    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY]
                        as RmApplication)
                val repository = application.container.repository
                ViewModel(repository = repository)
            }
        }
    }
}