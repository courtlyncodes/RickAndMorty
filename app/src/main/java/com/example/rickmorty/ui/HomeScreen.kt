package com.example.rickmorty.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.FlingBehavior
import androidx.compose.foundation.gestures.ScrollScope
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.navigation.rememberListDetailPaneScaffoldNavigator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import coil3.compose.AsyncImagePainter.State.Empty.painter
import com.example.rickmorty.R
import com.example.rickmorty.model.RmCharacter

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@Composable
fun ListDetailPane(modifier: Modifier = Modifier) {
    val navigator = rememberListDetailPaneScaffoldNavigator<Nothing>()

}

@Composable
fun ScreenContent(uiState: UiState, onCardClick: (RmCharacter) -> Unit) {
    when (uiState) {
        is UiState.Loading -> {
            LoadingScreen()
        }

        is UiState.Error -> {
            ErrorScreen()
        }

        is UiState.Success -> {
            CharacterList(uiState.rmCharacters, onCardClick)
        }
    }
}

@Composable
fun CharacterDetailCard(
    image: String,
    rmCharacter: RmCharacter,
    image2: Painter,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxSize()
            .padding(top = 160.dp)
    ) {
        AsyncImage(image, stringResource(R.string.character_image), contentScale = ContentScale.Crop,
            modifier = Modifier
                .width(200.dp)
                .height(200.dp)
                .clip(CircleShape)
        )
        Text(
            rmCharacter.name,
            fontSize = 32.sp,
            color = Color.Black,
            textAlign = TextAlign.Center,
            fontFamily = MaterialTheme.typography.displayLarge.fontFamily,
            modifier = modifier.padding(20.dp)
        )
        Text(
            "${rmCharacter.name} is ${rmCharacter.status.lowercase()}!",
            modifier.padding(16.dp),
            fontSize = 24.sp,
            fontFamily = MaterialTheme.typography.displayMedium.fontFamily,
            textAlign = TextAlign.Center
        )
        Text(
            "They are of the ${rmCharacter.species.lowercase()} species, and they are a ${rmCharacter.gender.lowercase()}.",
            modifier.padding(16.dp),
            fontSize = 20.sp,
            fontFamily = MaterialTheme.typography.displaySmall.fontFamily,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun CharacterList(
    rmCharacters: List<RmCharacter>,
    onCardClick: (RmCharacter) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn() {
        items(rmCharacters.size) {
            CharacterCard(
                rmCharacters[it],
                rmCharacters[it].image,
                rmCharacters[it].name,
                onCardClick
            )
        }
    }
}

@Composable
fun CharacterCard(
    rmCharacter: RmCharacter,
    icon: String,
    name: String,
    onCardClick: (RmCharacter) -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedCard(
        shape = CardDefaults.outlinedShape,
        elevation = CardDefaults.outlinedCardElevation(
            defaultElevation = 4.dp
        ),
        border = BorderStroke(1.dp, Color.Gray),
        colors = CardDefaults.cardColors(
            containerColor = Color.LightGray
        ),
        modifier = modifier
            .clickable { onCardClick(rmCharacter) }
            .padding(4.dp)
            .clip(RoundedCornerShape(50.dp))
    ) {
        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier
                .fillMaxWidth()

        ) {
            AsyncImage(
                icon,
                stringResource(R.string.character_image),
                contentScale = ContentScale.Crop,
                modifier = modifier.clip(
                    CircleShape
                )
            )
            Text(
                name,
                fontSize = 24.sp,
                color = Color.Black,
                textAlign = TextAlign.Center,
                fontFamily = MaterialTheme.typography.displayMedium.fontFamily,
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
fun CharacterListPreview() {
    val char = RmCharacter(
        id = 1,
        name = "Rick Sanchez",
        status = "Alive",
        species = "Human",
        gender = "Male",
        image = "https://placekitten.com/200/300", // If this is an image resource, it should be handled differently
    )

    val char2 = RmCharacter(
        id = 2,
        name = "Morty Smith",
        status = "Alive",
        species = "Human",
        gender = "Male",
        image = "https://placekitten.com/200/300"// If this is an image resource, it should be handled differently
    )
    val char3 = RmCharacter(
        id = 3,
        name = "Morty Smith",
        status = "Alive",
        species = "Human",
        gender = "Male",
        image = "https://rickandmortyapi.com/api/character/avatar/1.jpeg"// If this is an image resource, it should be handled differently
    )
    val char4 = RmCharacter(
        id = 4,
        name = "Morty Smith",
        status = "Alive",
        species = "Human",
        gender = "Male",
        image = "https://rickandmortyapi.com/api/character/avatar/1.jpeg"// If this is an image resource, it should be handled differently
    )
    val char5 = RmCharacter(
        id = 5,
        name = "Morty Smith",
        status = "Alive",
        species = "Human",
        gender = "Male",
        image = "https://placekitten.com/200/300"// If this is an image resource, it should be handled differently
    )
    val char6 = RmCharacter(
        id = 6,
        name = "Morty Smith",
        status = "Alive",
        species = "Human",
        gender = "Male",
        image = "https://placekitten.com/200/300"// If this is an image resource, it should be handled differently
    )
    val char7 = RmCharacter(
        id = 7,
        name = "Rick Sanchez",
        status = "Alive",
        species = "Human",
        gender = "Male",
        image = "https://placekitten.com/200/300" // If this is an image resource, it should be handled differently
    )
    val char8 = RmCharacter(
        id = 8,
        name = "Morty Smith",
        status = "Alive",
        species = "Human",
        gender = "Male",
        image = painterResource(R.drawable.img).toString()// If this is an image resource, it should be handled differently
    )
}