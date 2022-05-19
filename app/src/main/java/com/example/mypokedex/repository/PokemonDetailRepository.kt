package com.example.mypokedex.repository

import com.example.mypokedex.data.model.mainmodels.Pokemon
import com.example.mypokedex.data.model.mainmodels.PokemonSpecies
import com.example.mypokedex.network.ApiService
import com.example.mypokedex.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.lang.Exception

class PokemonDetailRepository(
    private val service: ApiService
) {

    fun getPokemonByName(name: String): Flow<Resource<Pokemon>> = flow {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data=service.getPokemonByName(name)))
        }catch (e: Exception){
            emit(Resource.error(data=null,message = e.message?:"Error occured"))
        }
    }

    fun getPokemonSpecies(id: Int): Flow<Resource<PokemonSpecies>> = flow {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data=service.getPokemonSpecies(id)))
        }catch (e: Exception){
            emit(Resource.error(data=null,message = e.message?:"Error occured"))
        }
    }
}