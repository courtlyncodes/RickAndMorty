package com.example.rickmorty.ui

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.palette.graphics.Palette
import coil3.compose.AsyncImage
import coil3.compose.AsyncImagePainter
import coil3.toBitmap
import com.example.rickmorty.R
import com.example.rickmorty.model.RmCharacter

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
                lineHeight = 44.sp,
                fontFamily = MaterialTheme.typography.displayLarge.fontFamily,
                modifier = modifier.padding(20.dp)
            )
            Text(
                stringResource(R.string.chardes, rmCharacter.name, rmCharacter.status.lowercase()),
                modifier.padding(16.dp),
                fontSize = 24.sp,
                lineHeight = 32.sp,
                fontFamily = MaterialTheme.typography.displayMedium.fontFamily,
                textAlign = TextAlign.Center
            )
            Text(
                stringResource(
                    R.string.chardes2,
                    rmCharacter.species.lowercase(),
                    rmCharacter.gender.lowercase()
                ),
                modifier.padding(16.dp),
                fontSize = 20.sp,
                fontFamily = MaterialTheme.typography.displaySmall.fontFamily,
                textAlign = TextAlign.Center
            )
        }
    }
}