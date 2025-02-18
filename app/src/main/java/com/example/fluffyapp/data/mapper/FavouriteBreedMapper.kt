package com.example.fluffyapp.data.mapper

import com.example.fluffyapp.data.local.entity.FavouriteBreedEntity
import com.example.fluffyapp.domain.model.FavouriteBreed

fun FavouriteBreedEntity.toFavouriteBreed(): FavouriteBreed{
    return FavouriteBreed(
        breedId = this.breedId,
        breedName = this.breedName,
        url = this.url,
        lifespan = this.lifespan
    )
}

fun FavouriteBreed.toFavouriteEntity(): FavouriteBreedEntity{
    return FavouriteBreedEntity(
        breedId = this.breedId,
        breedName = this.breedName,
        url = this.url,
        lifespan = this.lifespan
    )
}