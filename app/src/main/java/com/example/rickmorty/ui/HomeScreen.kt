package com.example.rickmorty.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.navigation.rememberListDetailPaneScaffoldNavigator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import coil3.compose.AsyncImage
import com.example.rickmorty.R
import com.example.rickmorty.model.Character
import coil3.compose.AsyncImage

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@Composable
fun ListDetailPane(modifier: Modifier = Modifier) {
    val navigator = rememberListDetailPaneScaffoldNavigator<Nothing>()

}

@Composable
fun ScreenContent(uiState: UiState, onCardClick: (Character) -> Unit) {
    when (uiState) {
        is UiState.Loading -> {
            LoadingScreen()
        }
        is UiState.Error -> {
            ErrorScreen()
        }
        is UiState.Success -> {
            CharacterList(uiState.characters, onCardClick)
        }
    }
}

@Composable
fun CharacterDetailCard(
    image: String,
    name: String,
    status: String,
    species: String,
    gender: String
) {
    Column {
        AsyncImage(image, stringResource(R.string.character_image))
        Text(name)
        Text(status)
        Text(species)
        Text(gender)
    }
}

@Composable
fun CharacterList(characters: List<Character>, onCardClick: (Character) -> Unit, modifier: Modifier = Modifier) {
    LazyColumn() {
        items(characters.size) {
            CharacterCard(characters[it], characters[it].image, characters[it].name, onCardClick)
        }
    }
}

@Composable
fun CharacterCard(character: Character, icon: String, name: String, onCardClick: (Character) -> Unit, modifier: Modifier = Modifier) {
    OutlinedCard(
        shape = CardDefaults.outlinedShape,
        colors = CardDefaults.cardColors(Color.Gray),
        modifier = modifier.clickable { onCardClick(character) }
    ) { }
    Row {
        AsyncImage(icon, stringResource(R.string.character_image))
        Text(name)
    }
}


@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
    Column(verticalArrangement = Arrangement.Center) {
        Text(stringResource(R.string.loading))
    }
}

@Composable
fun ErrorScreen(modifier: Modifier = Modifier) {
    Column(verticalArrangement = Arrangement.Center) {
        Text(stringResource(R.string.error))
    }
}