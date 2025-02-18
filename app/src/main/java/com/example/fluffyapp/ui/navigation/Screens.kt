package com.example.fluffyapp.ui.navigation

import kotlinx.serialization.Serializable
@Serializable
sealed class Screens(val route: String) {
    @Serializable
    data object BreedListScreen : Screens("cat_breed_screen")

    @Serializable
    data object FavouriteBreedScreen : Screens("cat_favourite_screen")
}
@Serializable
data class CatDetailScreen(val id: String)