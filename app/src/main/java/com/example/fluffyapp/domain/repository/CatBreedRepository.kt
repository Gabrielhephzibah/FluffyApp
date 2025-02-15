package com.example.fluffyapp.domain.repository

import com.example.fluffyapp.domain.model.CatBreed
import com.example.fluffyapp.utils.Resource
import kotlinx.coroutines.flow.Flow

interface CatBreedRepository {
    suspend fun getCat(): Flow<Resource<List<CatBreed>>>
}