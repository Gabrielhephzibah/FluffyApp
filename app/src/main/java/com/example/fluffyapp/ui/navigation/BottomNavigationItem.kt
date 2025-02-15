package com.example.fluffyapp.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.List
import androidx.compose.ui.graphics.vector.ImageVector

data class BottomNavigationItem(
    val title: String,
    val iconSelected: ImageVector,
    val iconNotSelected: ImageVector,
    val route: Screens,
) {
    companion object {
        val item = listOf(
            BottomNavigationItem(
                title = "Cats",
                iconSelected = Icons.Filled.List,
                iconNotSelected = Icons.Outlined.List,
                route = Screens.CatBreedListScreen
            ),
            BottomNavigationItem(
                title = "Favourites",
                iconSelected = Icons.Filled.Favorite,
                iconNotSelected = Icons.Outlined.Favorite,
                route = Screens.CatBreedListScreen
            )
        )
    }
}