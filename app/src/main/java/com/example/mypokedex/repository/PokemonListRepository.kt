package com.example.mypokedex.repository

import com.example.mypokedex.data.model.mainmodels.PokemonResponse
import com.example.mypokedex.network.ApiService
import com.example.mypokedex.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.lang.Exception

class PokemonListRepository
constructor(
    private val service: ApiService
){

    fun getPokemonList(limit: Int, offset: Int): Flow<Resource<PokemonResponse>> = flow {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data=service.getPokemonList(limit, offset)))
        }catch (e: Exception){
            emit(Resource.error(data=null,message = e.message?:"Error occured"))
        }
    }
}