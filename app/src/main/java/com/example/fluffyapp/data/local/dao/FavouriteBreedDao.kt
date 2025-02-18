package com.example.fluffyapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.fluffyapp.data.local.entity.FavouriteBreedEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FavouriteBreedDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavouriteBreed(breed: FavouriteBreedEntity)

    @Query("SELECT breedId FROM favouriteBreed")
    fun getFavouritesId(): Flow<List<String>>

    @Query("DELETE FROM favouriteBreed WHERE breedId = :id")
    suspend fun removeBreedFromFavourite(id: String)

    @Query("SELECT * FROM favouriteBreed ORDER BY breedName ASC")
    fun getFavouriteBreeds(): Flow<List<FavouriteBreedEntity>>

}