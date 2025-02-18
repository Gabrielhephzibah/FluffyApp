package com.example.fluffyapp.di

import com.example.fluffyapp.data.repository.BreedRepositoryImpl
import com.example.fluffyapp.domain.repository.BreedRepository
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
    abstract fun provideBreedRepository(breedRepositoryImpl: BreedRepositoryImpl): BreedRepository
}