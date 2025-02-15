package com.example.fluffyapp.data.remote.dto

import com.google.gson.annotations.SerializedName

data class CatDto(
    val id: String?,
    val breeds: List<Breed>?,
    val url : String?,
)

data class Breed(
    val id: String?,
    val name: String?,
    val description: String?,
    val temperament: String?,
    val origin: String?,
    @SerializedName("life_span")
    val lifeSpan: String?,
)
