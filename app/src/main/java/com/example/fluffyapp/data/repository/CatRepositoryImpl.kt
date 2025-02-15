package com.example.fluffyapp.data.repository

import com.example.fluffyapp.data.mapper.toCat
import com.example.fluffyapp.data.remote.CatApi
import com.example.fluffyapp.domain.model.Cat
import com.example.fluffyapp.domain.repository.CatRepository
import com.example.fluffyapp.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CatRepositoryImpl @Inject constructor(
    private val catApi: CatApi,
):CatRepository {
    override suspend fun getCat(): Flow<Resource<List<Cat>>>  =
       flow<Resource<List<Cat>>>{
           val response = catApi.getCat()
           emit(Resource.Success(response.map { it.toCat()}))
       }.catch {
              emit(Resource.Error(it))
       }.flowOn(Dispatchers.IO)
}