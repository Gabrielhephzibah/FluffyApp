package com.example.fluffyapp.data.mapper

import com.example.fluffyapp.data.local.entity.BreedEntity
import com.example.fluffyapp.data.remote.dto.BreedDto
import com.example.fluffyapp.domain.model.Breed

fun BreedDto.toCatBreedEntity(): BreedEntity{
    return BreedEntity(
        breedId = this.id ?: "",
        breedName = this.name ?: "",
        url = this.image?.url ?: "",
        lifespan = this.lifeSpan ?: "",
        origin = this.origin ?: "",
        temperament = this.temperament ?: "",
        description = this.description ?: ""
    )
}

fun BreedEntity.toCatBreed(): Breed{
    return Breed(
        breedId=this.breedId,
        breedName = this.breedName,
        url = this.url,
        lifespan = this.lifespan,
        origin = this.origin,
        temperament = this.temperament,
        description = this.description,
    )
}