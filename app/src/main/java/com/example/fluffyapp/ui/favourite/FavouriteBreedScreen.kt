package com.example.fluffyapp.ui.favourite

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil3.compose.AsyncImage
import com.example.fluffyapp.domain.model.FavouriteBreed

@Composable
fun FavouriteBreedScreen(paddingValues: PaddingValues, onItemClick: (String) -> Unit) {
    val viewModel: FavouriteBreedViewModel = hiltViewModel()
    val data = viewModel.state

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .padding(20.dp),
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp),
            textAlign = TextAlign.Center,
            text = "Average LifeSpan : ${data.averageLifeSpan}",
            color = Color.Magenta,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )
        if (data.favouriteBreed.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 20.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    text = "You do not have any favourite breed",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.DarkGray
                )
            }
        } else {
            Spacer(modifier = Modifier.padding(top = 30.dp))
            LazyVerticalGrid(
                columns = GridCells.Adaptive(150.dp),
            ) {
                items(
                    count = data.favouriteBreed.size,
                ) { i ->
                    val item = data.favouriteBreed[i]
                    println()
                    CatFavouriteItem(item = item, onItemClick)
                }

            }
        }
    }
}


@Composable
fun CatFavouriteItem(item: FavouriteBreed, onItemClick: (String) -> Unit) {
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
                },
            contentAlignment = Alignment.Center
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
            Icon(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(8.dp),
                imageVector = Icons.Filled.Favorite,
                contentDescription = "Favourite Item",
                tint = Color.Magenta
            )
        }
        Text(
            text = item.breedName,
            color = Color.Black,
            fontStyle = FontStyle.Normal,
            fontWeight = FontWeight.Bold,
        )
    }
}

@Preview(showBackground = true)
@Composable
fun FavouritePreview() {
    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        //FavouriteBreedScreen(PaddingValues(10.dp))
    }
}