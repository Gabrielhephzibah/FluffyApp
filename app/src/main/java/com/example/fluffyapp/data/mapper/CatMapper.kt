package com.example.fluffyapp.data.mapper

import com.example.fluffyapp.data.remote.dto.CatBreedDto
import com.example.fluffyapp.domain.model.Cat


fun CatBreedDto.toCat(): Cat {
    return Cat(
        breedName = this.name ?:"",
        url = this.image.url ?: "",
        lifespan = this.lifeSpan ?: "",
        origin = this.origin ?: "",
        temperament = this.temperament ?: "",
        description = this.description ?: ""
    )
}