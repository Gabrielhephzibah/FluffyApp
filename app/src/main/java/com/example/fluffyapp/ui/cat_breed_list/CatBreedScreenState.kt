package com.example.fluffyapp.ui.cat_breed_list

import com.example.fluffyapp.domain.model.CatBreed

data class CatBreedScreenState(
    val data : List<CatBreed> = emptyList(),
    val error : Throwable? = null,
    val loading : Boolean? = false
)