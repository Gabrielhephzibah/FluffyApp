package com.example.fluffyapp.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.fluffyapp.data.local.entity.CatBreedEntity

@Dao
interface CatBreedDao {
    @Query("SELECT * FROM catBreed")
    suspend fun getCatBreeds(): List<CatBreedEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCatBreeds(catBreed: List<CatBreedEntity>)

    @Query("DELETE FROM catBreed")
    suspend fun clearAll()
}