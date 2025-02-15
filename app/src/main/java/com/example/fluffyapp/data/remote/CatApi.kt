package com.example.fluffyapp.data.remote

import com.example.fluffyapp.data.remote.dto.CatDto
import retrofit2.http.GET
import retrofit2.http.Query

interface CatApi {
    @GET("v1/images/search")
    suspend fun getCat(@Query("has_breeds") hasBread: Boolean = true): List<CatDto >
}