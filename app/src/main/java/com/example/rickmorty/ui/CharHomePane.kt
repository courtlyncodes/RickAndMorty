package com.example.rickmorty.ui

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.isTraversalGroup
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.traversalIndex
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.example.rickmorty.R
import com.example.rickmorty.model.RmCharacter
import kotlin.math.exp

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

    Column(modifier = modifier.padding(top = 16.dp)) {

        TopAppBar()


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

        FilterMenu()

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
fun FilterMenu(onFilterSelected: (String, String) -> Unit) {
    val filters = listOf(
        FilterData("Gender", listOf("Male", "Female")),
        FilterData("Species", listOf("Human", "Alien", "Robot", "Other")),
        FilterData("Status", listOf("Alive", "Dead", "Unknown"))
    )
    Row {
        filters.forEach { filter ->
            var expanded by remember { mutableStateOf(false) }
            FilterButton(
                filter.title,
                filter.subOptions,
                expanded = expanded,
                onExpandChange = { expanded = it },
                onOptionSelected = { option ->
                    onFilterSelected(filter.title, option)
                },
            )

        }
    }
}

@Composable
fun FilterButton(
    title: String,
    subOptions: List<String>,
    expanded: Boolean,
    onExpandChange: (Boolean) -> Unit,
    onOptionSelected: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Box {
        ElevatedButton(onClick = { onExpandChange(!expanded) }) {
            Text(title)
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { onExpandChange(false) }
        ) {
            subOptions.forEach { subOption ->
                DropdownMenuItem(
                    text = { Text(subOption) },
                    onClick = {
                        onOptionSelected(subOption)
                        onExpandChange(false)
                    }
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBar() {
    CenterAlignedTopAppBar(
        title = {
            Box {
                Text(
                    text = stringResource(R.string.rick_morty_finder),
                    fontFamily = MaterialTheme.typography.titleLarge.fontFamily,
                    color = Color(0xFF8bcf21)
                )

            }
        }
    )
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

data class FilterData(
    val title: String,
    val subOptions: List<String>
)
