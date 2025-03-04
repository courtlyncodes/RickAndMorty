package com.example.rickmorty

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.rickmorty.model.RmCharacter
import com.example.rickmorty.ui.CharacterCard
import com.example.rickmorty.ui.theme.RickMortyTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RickMortyTheme {
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
                        icon = "https://placekitten.com/200/300",
                        name = char.name,
                        onCardClick = { TODO() },
                        modifier = Modifier
                    )
                    CharacterCard(
                        rmCharacter = char2,
                        icon = "https://placekitten.com/200/300",
                        name = char2.name,
                        onCardClick = { TODO() },
                        modifier = Modifier
                    )
                }
            }
        }
    }
}
