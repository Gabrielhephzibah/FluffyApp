package com.example.fluffyapp.ui.detail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import com.example.fluffyapp.domain.model.FavouriteBreed

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BreedDetailScreen(breedId: String, navController: NavController) {
    val viewModel : BreedDetailViewModel = hiltViewModel()
    viewModel.getBreedDetail(breedId)

    val breed  = viewModel.state.breedDetail
    var isFavorite by rememberSaveable { mutableStateOf(breed.isFavorite) }
    LaunchedEffect(breed.isFavorite) {
        isFavorite = breed.isFavorite
    }

    Scaffold(
        topBar = {
           CenterAlignedTopAppBar(
               modifier = Modifier.padding(top = 10.dp),
               title = {
               Text(text = breed.breedName,
                   fontSize = 18.sp,
                   fontWeight = FontWeight.Bold,
                     color = MaterialTheme.colorScheme.onSecondary
               )
           },
               navigationIcon = {
                   IconButton(onClick = { navController.popBackStack() }) {
                       Icon(
                           imageVector = Icons.Filled.ArrowBack,
                           contentDescription = "Back button"
                       )
                   }
               },
           )
        }
    ) { innerPadding ->
        Column(modifier = Modifier
            .padding(innerPadding)
            .fillMaxSize()
            .padding(horizontal = 20.dp)) {
          IconButton(
              modifier = Modifier.align(Alignment.End),
              onClick = {
                    isFavorite = !isFavorite
                  if (isFavorite){
                      viewModel.insertFavourite(
                          FavouriteBreed(
                          breedId = breed.breedId,
                          breedName = breed.breedName,
                          url = breed.url,
                          lifespan = breed.lifespan)
                      )
                  }else{
                      viewModel.removeFavourite(breed.breedId)
                  }
              }) {
              Icon(modifier = Modifier.size(35.dp),
                  imageVector = if(isFavorite) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                  tint = Color.Magenta,
                  contentDescription = "favourite")
          }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AsyncImage(
                    modifier = Modifier
                        .size(200.dp)
                        .clip(RoundedCornerShape(8.dp)),
                    model = breed.url,
                    contentScale = ContentScale.FillBounds,
                    contentDescription = "cat-image",
                    clipToBounds = true
                )
                Text(
                    modifier = Modifier.padding(top = 8.dp),
                    text = breed.origin,
                    fontStyle = FontStyle.Italic,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    modifier = Modifier.padding(10.dp),
                    text = breed.temperament,
                    fontSize = 14.sp
                    )
                Text(
                    textAlign = TextAlign.Start,
                    fontSize = 15.sp,
                    lineHeight = 22.sp,
                    text = breed.description)
            }

        }
    }
}