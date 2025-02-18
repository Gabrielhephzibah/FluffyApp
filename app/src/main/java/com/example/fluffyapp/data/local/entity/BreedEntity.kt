package com.example.fluffyapp.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "breed")
data class BreedEntity(
    @PrimaryKey
    val breedId : String,
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
    val page : Int? = null
)