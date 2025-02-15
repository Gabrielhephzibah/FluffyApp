package com.example.fluffyapp.ui.navigation

import kotlinx.serialization.Serializable

sealed class Screens {
    @Serializable
    data object CatListScreen : Screens()

    @Serializable
    data object CatFavouriteScreen : Screens()

    @Serializable
    data object CatDetailScreen : Screens()
}
