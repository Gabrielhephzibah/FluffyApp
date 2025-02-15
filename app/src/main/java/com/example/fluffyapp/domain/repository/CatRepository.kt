package com.example.fluffyapp.domain.repository

import com.example.fluffyapp.domain.model.Cat
import com.example.fluffyapp.utils.Resource
import kotlinx.coroutines.flow.Flow

interface CatRepository {
    suspend fun getCat(): Flow<Resource<List<Cat>>>
}