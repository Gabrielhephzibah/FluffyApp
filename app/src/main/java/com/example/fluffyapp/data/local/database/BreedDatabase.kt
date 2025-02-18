package com.example.fluffyapp.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.fluffyapp.data.local.dao.BreedDao
import com.example.fluffyapp.data.local.dao.FavouriteBreedDao
import com.example.fluffyapp.data.local.entity.BreedEntity
import com.example.fluffyapp.data.local.entity.FavouriteBreedEntity

@Database(entities = [BreedEntity::class, FavouriteBreedEntity::class], version = 1, exportSchema = false)
abstract class BreedDatabase : RoomDatabase() {
    abstract fun breedDao(): BreedDao
    abstract fun favouriteBreedDao(): FavouriteBreedDao
}