package com.example.fluffyapp.ui.breed

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.example.fluffyapp.domain.model.Breed
import com.example.fluffyapp.domain.model.FavouriteBreed

@Composable
fun BreedItem(item: Breed, viewModel: BreedViewModel, onItemClick: (String) -> Unit) {
    var isFavorite by rememberSaveable { mutableStateOf(item.isFavourite) }

    LaunchedEffect(isFavorite) {
        isFavorite = item.isFavourite
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(10.dp))
                .aspectRatio(1f)
                .padding(8.dp)
                .clickable {
                    onItemClick(item.breedId)
                }
        ) {
            AsyncImage(
                modifier = Modifier
                    .aspectRatio(1f)
                    .clip(RoundedCornerShape(8.dp)),
                model = item.url,
                contentScale = ContentScale.FillBounds,
                contentDescription = "cat-image",
                clipToBounds = true
            )
            IconButton(
                onClick = {
                    isFavorite = !isFavorite
                    if (isFavorite) {
                        viewModel.insertFavouriteBreed(
                            FavouriteBreed(
                                breedId = item.breedId,
                                breedName = item.breedName,
                                url = item.url,
                                lifespan = item.lifespan
                            )
                        )
                    } else {
                        viewModel.removeFavouriteBreed(item.breedId)
                    }
                },
                modifier = Modifier
                    .align(Alignment.TopEnd)
            ) {
                Icon(modifier = Modifier.size(27.dp),
                    imageVector = if (isFavorite) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                    tint = Color.Magenta,
                    contentDescription = "favourites"
                )
            }
        }
        Text(
            text = item.breedName,
            color = MaterialTheme.colorScheme.onSecondary,
            fontStyle = FontStyle.Normal,
            fontWeight = FontWeight.Bold,
        )
    }
}