package com.example.fluffyapp.di

import com.example.fluffyapp.data.repository.CatRepositoryImpl
import com.example.fluffyapp.domain.repository.CatRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun provideCatRepository(catRepositoryImpl: CatRepositoryImpl): CatRepository
}