package com.example.fluffyapp.data.remote.dto

import com.google.gson.annotations.SerializedName

data class CatBreedDto(
    val id: String?,
    val name: String?,
    val description: String?,
    val temperament: String?,
    val origin: String?,
    @SerializedName("life_span")
    val lifeSpan: String?,
    val image: Image
)
data class Image(
    val id: String?,
    val url: String?
)
