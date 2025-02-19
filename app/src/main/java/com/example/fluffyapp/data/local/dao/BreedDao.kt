package com.example.fluffyapp.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.example.fluffyapp.data.local.entity.BreedEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface BreedDao {
    @Query("SELECT * FROM breed WHERE (:breedName IS NULL OR breedName LIKE '%' || :breedName || '%') ORDER BY breedName ASC")
     fun getBreeds(breedName: String? = null): PagingSource<Int, BreedEntity>

    @Query("SELECT * FROM breed WHERE breedId = :breedId")
    fun findBreedById(breedId: String): Flow<BreedEntity>

    @Upsert
    suspend fun upsertBreeds(catBreed: List<BreedEntity>)

    @Query("DELETE FROM breed")
    suspend fun clearAllBreed()
}