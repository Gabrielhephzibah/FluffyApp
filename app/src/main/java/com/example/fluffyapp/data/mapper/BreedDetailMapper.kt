package com.example.fluffyapp.data.mapper

import com.example.fluffyapp.data.local.entity.BreedEntity
import com.example.fluffyapp.domain.model.BreedDetail

fun BreedEntity.toBreedDetail(): BreedDetail {
    return BreedDetail(
        breedId = this.breedId,
        breedName = this.breedName,
        url = this.url,
        origin = this.origin,
        temperament = this.temperament,
        description = this.description,
        lifespan = this.lifespan
    )
}