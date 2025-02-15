package com.example.fluffyapp.ui.cat_list

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil3.compose.AsyncImage

@Composable
fun CatListScreen(paddingValues: PaddingValues, viewModel: CatListViewModel = hiltViewModel()) {
    Scaffold(
        modifier = Modifier.padding(paddingValues),
        topBar = {
            SearchTopBar()
        }
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            val catList = viewModel.state.data
            LazyVerticalGrid(
                columns = GridCells.Adaptive(150.dp)
            ) {
                items(catList.size) { i ->
                    val item = catList[i]
                    CatListItem(item.url, item.breedName)
                }
            }
        }

    }

}


@Composable
fun SearchTopBar() {
    var searchQuery by remember {
        mutableStateOf("")
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 20.dp)
    ) {
        TextField(
            value = searchQuery,
            onValueChange = {
                            searchQuery = it
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp)
                .onKeyEvent {
                    true
                },
            placeholder = { Text(text = "Search by breed name") },
            singleLine = true,
            maxLines = 1,
            keyboardActions = KeyboardActions(
                onDone = {
                },
            ),
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
            ),

            trailingIcon = {
                IconButton(onClick = {

                }) {
                    Icon(imageVector = Icons.Default.Search, contentDescription = "Search")
                }
            },
            shape = RoundedCornerShape(22.dp)
        )

    }

}


@Composable
fun CatListItem(image: String, name: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(10.dp))
                .aspectRatio(1f)
                .padding(8.dp),
            contentAlignment = Alignment.Center
        ) {
            AsyncImage(
                modifier = Modifier
                    .fillMaxHeight()
                    .clip(RoundedCornerShape(8.dp)),
                model = image,
                contentScale = ContentScale.FillBounds,
                contentDescription = "cat-image",
                clipToBounds = true
            )
            Icon(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(end = 10.dp, top = 10.dp),
                imageVector = Icons.Filled.Favorite, contentDescription = "favourites"
            )
        }
        Text(
            text = name,
            color = Color.Black,
            fontStyle = FontStyle.Normal,
            fontWeight = FontWeight.Bold,
        )

    }
}

@Preview(showBackground = true)
@Composable
fun CatListPreview() {


}