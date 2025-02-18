package com.example.fluffyapp.ui.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.example.fluffyapp.ui.detail.BreedDetailScreen
import com.example.fluffyapp.ui.favourite.FavouriteBreedScreen
import com.example.fluffyapp.ui.breed.CatBreedListScreen

@Composable
fun AppNavigation(navController: NavHostController, paddingValues: PaddingValues) {
    NavHost(
        navController = navController,
        startDestination = Screens.BreedListScreen.route
    ) {
        composable(
            route = Screens.BreedListScreen.route)    {
            CatBreedListScreen(paddingValues){
                navController.navigate(CatDetailScreen(it))
            }
        }

        composable(route = Screens.FavouriteBreedScreen.route) {
           FavouriteBreedScreen(paddingValues){
               navController.navigate(CatDetailScreen(it))
           }
        }

        composable<CatDetailScreen>{
            val argument = it.toRoute<CatDetailScreen>()
            BreedDetailScreen(breedId = argument.id, navController)
        }
    }
}