package com.example.rickmorty.ui

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffold
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffoldRole
import androidx.compose.material3.adaptive.navigation.rememberListDetailPaneScaffoldNavigator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.palette.graphics.Palette
import coil3.compose.AsyncImage
import coil3.compose.AsyncImagePainter
import coil3.toBitmap
import com.example.rickmorty.R
import com.example.rickmorty.model.RmCharacter
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@Composable
fun ListDetailPane(
    rmCharacters: List<RmCharacter>, modifier: Modifier = Modifier)
{
    val navigator = rememberListDetailPaneScaffoldNavigator<RmCharacter>()
    val scope = rememberCoroutineScope()

    BackHandler(navigator.canNavigateBack()) {
        scope.launch {  navigator.navigateBack() }

    }

    ListDetailPaneScaffold(
        directive = navigator.scaffoldDirective,
        value = navigator.scaffoldValue,
        listPane = {
            CharacterList(rmCharacters, onCardClick = { character ->
                scope.launch {
                    navigator.navigateTo(ListDetailPaneScaffoldRole.Detail, character)
                }
            })
        },
        detailPane = {
            val character = navigator.currentDestination?.contentKey
            character?.let {
                CharacterDetailCard(rmCharacter = it)
            }
        }
    )
}

@Composable
fun ScreenContent(
    viewModel: ViewModel = viewModel(factory = ViewModel.Factory)
) {
    when (val uiState = viewModel.uiState) {
        is UiState.Loading -> {
            LoadingScreen()
        }

        is UiState.Error -> {
            ErrorScreen()
        }

        is UiState.Success -> {
            ListDetailPane(uiState.rmCharacters)
        }
    }
}

@Composable
fun CharacterDetailCard(
    rmCharacter: RmCharacter,
    modifier: Modifier = Modifier
) {
    var prominentColor by remember { mutableStateOf(Color.White) }
    Box(
        modifier
            .fillMaxSize()
    ) {
        Column(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier
                .fillMaxSize()
                .padding(top = 160.dp)
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = modifier
                    .width(220.dp)
                    .height(220.dp)
                    .clip(CircleShape)
                    .border(10.dp, prominentColor, CircleShape)
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = modifier
                        .width(210.dp)
                        .height(210.dp)
                        .clip(CircleShape)
                        .border(10.dp, Color.White, CircleShape)
                )
                {
                    AsyncImage(
                        rmCharacter.image,
                        stringResource(R.string.character_image),
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .width(200.dp)
                            .height(200.dp)
                            .clip(CircleShape),
                        onState = { state ->
                            if (state is AsyncImagePainter.State.Success) {
                                // Convert the drawable to a mutable bitmap to extract palette
                                val drawable = state.result.image
                                val bitmap = (drawable as? BitmapDrawable)?.bitmap
                                    ?: (drawable.toBitmap() // Fallback to toBitmap() if not a BitmapDrawable)
                                        .copy(Bitmap.Config.ARGB_8888, true))

                                // Generate the palette from the bitmap
                                val palette = Palette.from(bitmap).generate()

                                // Set the background color from the dominant swatch (if available)
                                palette.dominantSwatch?.let { swatch ->
                                    prominentColor = Color(swatch.rgb)
                                }
                            }
                        }
                    )
                }
            }
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
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CharacterList(
    rmCharacters: List<RmCharacter>,
    onCardClick: (RmCharacter) -> Unit,
    modifier: Modifier = Modifier
) {
    Column {
        CenterAlignedTopAppBar(
            title = {
                Box {
                    Text(
                        text = "Rick & Morty Finder",
                        fontFamily = MaterialTheme.typography.titleLarge.fontFamily,
                        color = Color(0xFF83d2e4),
                        modifier = modifier
                    )

                }
            }
        )
        LazyVerticalGrid(columns = GridCells.Fixed(2)) {
            items(rmCharacters.size) {
                CharacterCard(
                    rmCharacters[it],
                    rmCharacters[it].image,
                    rmCharacters[it].name,
                    onCardClick,
                    modifier
                )
            }
        }
    }
}

@Composable
fun CharacterCard(
    rmCharacter: RmCharacter,
    image: String,
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
            containerColor = Color(0xFFEAEAEA)
        ),
        modifier = modifier
            .clickable { onCardClick(rmCharacter) }
            .padding(4.dp)
            .height(186.dp)
            .clip(RoundedCornerShape(10.dp))
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = modifier
                .fillMaxWidth()

        ) {
            AsyncImage(
                image,
                stringResource(R.string.character_image),
                contentScale = ContentScale.Crop,
                modifier = modifier.padding(16.dp).clip(RoundedCornerShape(10.dp))
            )

            Text(
                name,
                fontSize = 16.sp,
                color = Color.Black,
                textAlign = TextAlign.Center,
                fontFamily = MaterialTheme.typography.displayMedium.fontFamily,
                modifier = modifier
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