package com.example.fluffyapp.data.remote

import com.example.fluffyapp.BuildConfig
import com.example.fluffyapp.data.remote.dto.BreedDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface BreedApi {
    @GET("breeds")
    suspend fun getBreeds(
        @Query("limit") limit: Int,
        @Query("page") page: Int?,
        @Query("order") order: String = "ASC",
        @Header("x-api-key") apiKey: String = BuildConfig.API_KEY
    ): Response<List<BreedDto>>
}