package com.example.fluffyapp.domain.model

data class BreedDetail(
    val breedId: String = "",
    val breedName: String = "",
    val url: String = "",
    val origin: String = "",
    val temperament: String = "",
    val description: String = "",
    val lifespan: String = "",
    val isFavorite: Boolean = false,
)