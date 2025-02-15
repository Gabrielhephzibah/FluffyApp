package com.example.fluffyapp.data.repository

import com.example.fluffyapp.data.mapper.toCat
import com.example.fluffyapp.data.remote.CatBreedApi
import com.example.fluffyapp.domain.model.CatBreed
import com.example.fluffyapp.domain.repository.CatBreedRepository
import com.example.fluffyapp.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CatBreedRepositoryImpl @Inject constructor(
    private val catBreedApi: CatBreedApi,
):CatBreedRepository {
    override suspend fun getCat(): Flow<Resource<List<CatBreed>>>  =
       flow<Resource<List<CatBreed>>>{
           val response = catBreedApi.getCat()
           emit(Resource.Success(response.map { it.toCat()}))
       }.catch {
              emit(Resource.Error(it))
       }.flowOn(Dispatchers.IO)
}