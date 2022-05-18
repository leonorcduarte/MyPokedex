package com.example.mypokedex.di

import com.example.mypokedex.network.ApiService
import com.example.mypokedex.repository.PokemonListRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun providePokemonListRepository(
        service: ApiService
    ): PokemonListRepository{
        return PokemonListRepository(service)
    }
}