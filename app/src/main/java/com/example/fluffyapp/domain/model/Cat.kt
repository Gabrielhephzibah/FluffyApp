package com.example.fluffyapp.domain.model

data class Cat(
    val breedName: String,
    val url: String,
    val lifespan: String,
    val origin: String,
    val temperament: String,
    val description: String,
    val isFavourite: Boolean = false,
)
