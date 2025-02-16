package com.example.fluffyapp.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.fluffyapp.data.local.dao.CatBreedDao
import com.example.fluffyapp.data.local.entity.CatBreedEntity

@Database(entities = [CatBreedEntity::class], version = 1, exportSchema = false)
abstract class CatBreedDatabase : RoomDatabase() {
    abstract fun CatBreedDao(): CatBreedDao
}