package com.example.fluffyapp.domain.model

data class Breed(
    val breedId: String,
    val breedName: String,
    val url: String,
    val lifespan: String,
    val isFavourite: Boolean = false,
)
