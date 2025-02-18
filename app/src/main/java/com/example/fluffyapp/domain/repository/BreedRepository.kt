package com.example.fluffyapp.domain.repository

import androidx.paging.PagingData
import com.example.fluffyapp.domain.model.BreedDetail
import com.example.fluffyapp.domain.model.Breed
import com.example.fluffyapp.domain.model.FavouriteBreed
import kotlinx.coroutines.flow.Flow

interface BreedRepository {
    fun getBreeds(searchQuery: String?): Flow<PagingData<Breed>>
    suspend fun insertFavouriteBreed(favouriteBreed: FavouriteBreed)
    fun getFavoriteBreedId(): Flow<List<String>>
    suspend fun removeBreedFromFavourite(id: String)
    fun getFavoriteBreeds(): Flow<List<FavouriteBreed>>
    fun getBreedDetail(id:String): Flow<BreedDetail>
}