package com.example.mypokedex.network

import com.example.mypokedex.data.model.mainmodels.Pokemon
import com.example.mypokedex.data.model.mainmodels.PokemonResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("pokemon/{name}")
    suspend fun getPokemonByNameList(
        @Path("name") name: String
    ): Pokemon

    @GET("pokemon/")
    suspend fun getPokemonList(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): PokemonResponse
}