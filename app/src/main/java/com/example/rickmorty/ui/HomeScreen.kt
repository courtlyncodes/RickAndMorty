package com.example.rickmorty.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.navigation.rememberListDetailPaneScaffoldNavigator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.example.rickmorty.R
import com.example.rickmorty.model.RmCharacter

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@Composable
fun ListDetailPane(modifier: Modifier = Modifier) {
    val navigator = rememberListDetailPaneScaffoldNavigator<Nothing>()

}
//
//@Composable
//fun ScreenContent(uiState: UiState, onCardClick: (RmCharacter) -> Unit) {
//    when (uiState) {
//        is UiState.Loading -> {
//            LoadingScreen()
//        }
//
//        is UiState.Error -> {
//            ErrorScreen()
//        }
//
//        is UiState.Success -> {
//            CharacterList(uiState.rmCharacters, onCardClick)
//        }
//    }
//}

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
//
//@Composable
//fun CharacterList(
//    rmCharacters: List<RmCharacter>,
//    onCardClick: (RmCharacter) -> Unit,
//    modifier: Modifier = Modifier
//) {
//    LazyColumn() {
//        items(rmCharacters.size) {
//            CharacterCard(
//                rmCharacters[it],
//                rmCharacters[it].image,
//                rmCharacters[it].name,
//                onCardClick
//            )
//        }
//    }
//}

@Composable
fun CharacterCard(
    rmCharacter: RmCharacter,
    icon: Painter,
    name: String,
    onCardClick: (RmCharacter) -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedCard(
        shape = CardDefaults.outlinedShape,
        elevation = CardDefaults.outlinedCardElevation(
            defaultElevation = 18.dp
        ),
        border = BorderStroke(1.dp, Color.Gray),
        colors = CardDefaults.cardColors(
            containerColor = Color.LightGray
        ),
        modifier = modifier
            .clickable { onCardClick(rmCharacter) }
    ) {
        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier.fillMaxWidth()
        ) {
//        AsyncImage(icon, stringResource(R.string.character_image))
            Image(icon, stringResource(R.string.character_image))
            Text(
                name,
                fontSize = 24.sp,
                color = Color.Black,
                textAlign = TextAlign.Center,
                modifier = modifier.padding(16.dp)
            )
        }
    }
}


@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.fillMaxSize()
    ) {
        Text(
            stringResource(R.string.loading),
            fontSize = 32.sp,
            color = Color.Blue,
            modifier = modifier
        )
    }
}

@Composable
fun ErrorScreen(modifier: Modifier = Modifier) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.fillMaxSize()
    ) {
        Text(
            stringResource(R.string.error), fontSize = 32.sp,
            color = Color.Red,
            modifier = modifier
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun CharacterCardPreview() {
    val char = RmCharacter(
        id = 1,
        name = "Rick Sanchez",
        status = "Alive",
        species = "Human",
        gender = "Male",
        image = R.drawable.img.toString() // If this is an image resource, it should be handled differently
    )

    val char2 = RmCharacter(
        id = 2,
        name = "Morty Smith",
        status = "Alive",
        species = "Human",
        gender = "Male",
        image = R.drawable.img.toString() // If this is an image resource, it should be handled differently
    )
    Column {
        CharacterCard(
            rmCharacter = char,
            icon = painterResource(R.drawable.img),
            name = char.name,
            onCardClick = { TODO() },
            modifier = Modifier
        )
        CharacterCard(
            rmCharacter = char2,
            icon = painterResource(R.drawable.img),
            name = char2.name,
            onCardClick = { TODO() },
            modifier = Modifier
        )
    }
}