package com.example.fluffyapp.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favouriteBreed")
data class FavouriteBreedEntity(
    @PrimaryKey
    val breedId : String,
    @ColumnInfo
    val breedName : String,
    @ColumnInfo
    val url : String,
    @ColumnInfo
    val lifespan: String,
)