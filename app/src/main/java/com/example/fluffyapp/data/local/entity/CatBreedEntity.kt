package com.example.fluffyapp.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "catBreed")
data class CatBreedEntity(
    @PrimaryKey(autoGenerate = true)
    val id : Int = 0,
    @ColumnInfo
    val breedName : String,
    @ColumnInfo
    val url : String,
    @ColumnInfo
    val lifespan: String,
    @ColumnInfo
    val origin: String,
    @ColumnInfo
    val temperament: String,
    @ColumnInfo
    val description : String,
    @ColumnInfo
    val isFavourite: Boolean = false
)