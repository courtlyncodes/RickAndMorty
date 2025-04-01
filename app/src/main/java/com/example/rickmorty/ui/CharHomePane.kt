package com.example.rickmorty.ui

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffold
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffoldRole
import androidx.compose.material3.adaptive.navigation.rememberListDetailPaneScaffoldNavigator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.isTraversalGroup
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.traversalIndex
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.AsyncImage
import com.example.rickmorty.R
import com.example.rickmorty.model.RmCharacter
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CharacterList(
    rmCharacters: List<RmCharacter>,
    onCharSearch: (String) -> Unit,
    onCardClick: (RmCharacter) -> Unit,
    modifier: Modifier = Modifier
) {
    var text by rememberSaveable { mutableStateOf("") }
    var expanded by rememberSaveable { mutableStateOf(false) }

    Column {
        CenterAlignedTopAppBar(
            title = {
                Box {
                    Text(
                        text = stringResource(R.string.rick_morty_finder),
                        fontFamily = MaterialTheme.typography.titleLarge.fontFamily,
                        color = Color(0xFF8bcf21),
                        modifier = modifier
                    )

                }
            }
        )
        Box(
            modifier
                .fillMaxWidth()
                .semantics { isTraversalGroup = true }
                .padding(bottom = 16.dp)
        ) {
            SearchBar(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .semantics { traversalIndex = 0f },
                inputField = {
                    SearchBarDefaults.InputField(
                        query = text,
                        onQueryChange = {
                            text = it
                            onCharSearch(text)
                        },
                        onSearch = {
                            expanded = false
                        },
                        expanded = expanded,
                        onExpandedChange = { expanded = it },
                        placeholder = { Text(stringResource(R.string.search)) },
                        leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                    )
                },
                expanded = false,
                onExpandedChange = { expanded = false },
            ) {
            }
        }
        LazyVerticalGrid(
            columns = GridCells.FixedSize(210.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = modifier.padding(bottom = 32.dp)
        ) {
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
            containerColor = Color(0xFFCCE2E8)
        ),
        modifier = modifier
            .clickable { onCardClick(rmCharacter) }
            .padding(4.dp)
            .aspectRatio(.8f)
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
//                contentScale = ContentScale.Crop,
                modifier = modifier
                    .padding(top = 16.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .aspectRatio(7 / 6f)
            )

            Text(
                name,
                fontSize = 16.sp,
                modifier = modifier.padding(4.dp),
                color = Color.Black,
                textAlign = TextAlign.Center,
                fontFamily = MaterialTheme.typography.displayMedium.fontFamily,
                maxLines = 2
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
