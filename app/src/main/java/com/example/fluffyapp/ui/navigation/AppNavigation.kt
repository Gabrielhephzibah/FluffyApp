package com.example.fluffyapp.ui.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.fluffyapp.ui.cat_breed_list.CatBreedListScreen

@Composable
fun AppNavigation(navController: NavHostController, paddingValues: PaddingValues) {
    NavHost(
        navController = navController,
        startDestination = Screens.CatBreedListScreen
    ) {
        composable<Screens.CatBreedListScreen> {
            CatBreedListScreen(paddingValues)
        }
    }
}