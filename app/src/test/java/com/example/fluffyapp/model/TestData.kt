package com.example.fluffyapp.model

import com.example.fluffyapp.data.local.entity.BreedEntity
import com.example.fluffyapp.data.local.entity.FavouriteBreedEntity
import com.example.fluffyapp.domain.model.FavouriteBreed

object TestData {
    val id = "abys"
    val favouriteBreed = FavouriteBreed(
        breedId = "abys",
        breedName = "Abyssinian",
        url = "https://cdn2.thecatapi.com/images/0XYvRd7oD.jpg",
        lifespan = "3 - 5"
    )

    val favouriteId = listOf("abys", "aege", "abob")

    val favouriteBreedEntity = listOf( FavouriteBreedEntity(
        breedId = "abys",
        breedName = "Abyssinian",
        url = "https://cdn2.thecatapi.com/images/0XYvRd7oD.jpg",
        lifespan = "3 - 5"
    ))

    val breedEntity = BreedEntity(
        breedId = "abys",
        breedName = "Abyssinian",
        url = "https://cdn2.thecatapi.com/images/0XYvRd7oD.jpg",
        origin = "Egypt",
        temperament = "Active, Energetic,",
        description = "The Abyssinian is easy to care for",
        lifespan = "3 - 5",
    )






}