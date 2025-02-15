package com.example.fluffyapp.data.remote

import com.example.fluffyapp.BuildConfig
import com.example.fluffyapp.data.remote.dto.CatBreedDto
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface CatBreedApi {
    @GET("breeds")
    suspend fun getCat(
        @Query("limit") limit: Int = 20,
        @Query("page") page: Int = 0,
        @Query("order") order: String = "ASC",
        @Header("x-api-key") apiKey: String = BuildConfig.API_KEY
    ): List<CatBreedDto>
}