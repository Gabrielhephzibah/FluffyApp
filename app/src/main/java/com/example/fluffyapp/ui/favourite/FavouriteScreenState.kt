package com.example.fluffyapp.ui.favourite

import com.example.fluffyapp.domain.model.FavouriteBreed

data class FavouriteScreenState(
    val favouriteBreed: List<FavouriteBreed> = emptyList(),
    val averageLifeSpan: String? = null,
)