package com.example.rickmorty.ui

import androidx.activity.compose.BackHandler
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
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.rickmorty.model.RmCharacter
import kotlinx.coroutines.launch

@Composable
fun ScreenContent(
    viewModel: ViewModel = viewModel(factory = ViewModel.Factory)
) {
    var text by remember { mutableStateOf("") }

    when (val uiState = viewModel.uiState) {
        is UiState.Loading -> {
            LoadingScreen()
        }

        is UiState.Error -> {
            ErrorScreen()
        }

        is UiState.Success -> {
            ListDetailPane(uiState.rmCharacters, viewModel::searchChars)
        }
    }
}

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@Composable
fun ListDetailPane(
    rmCharacters: List<RmCharacter>, onCharSearch: (String) -> Unit, modifier: Modifier = Modifier
)
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
            CharacterList(
                rmCharacters,
                onCharSearch = { name -> onCharSearch(name) },
                onCardClick = { character ->
                scope.launch {
                    navigator.navigateTo(ListDetailPaneScaffoldRole.Detail, character)
                }
            },
                toDetailPage = { character ->
                    scope.launch { navigator.navigateTo(ListDetailPaneScaffoldRole.Detail, character)}
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