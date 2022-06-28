package com.example.mypokedex.pokeList

import com.example.mypokedex.data.model.mainmodels.PokemonResponse
import com.example.mypokedex.network.ApiService
import com.example.mypokedex.repository.PokemonListRepository
import com.example.mypokedex.utils.BaseUnitTest
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.Assert.assertEquals
import java.lang.RuntimeException

class PokeListRepositoryShould : BaseUnitTest() {

    private val service: ApiService = mock()
    private val pokemonResponse = mock<PokemonResponse>()
    private val exception = RuntimeException("Error occurred")

    @Test
    fun getPokemonListFromService(): Unit = runBlocking {
        val repository = PokemonListRepository(service)

        repository.getPokemonList().toList()[1]

        verify(service, times(1)).getPokemonList()
    }

    @Test
    fun emitPokemonListFromService(): Unit = runBlocking {
        val repository = mockSuccessfulCase()

        assertEquals(pokemonResponse, repository.getPokemonList().toList()[1].data)
    }

    @Test
    fun propagateErrors(): Unit = runBlocking {
        val repository = mockFailureCase()

        assertEquals(exception.message, repository.getPokemonList().toList().last().message)
    }

    private suspend fun mockFailureCase(): PokemonListRepository {
        whenever(service.getPokemonList()).thenThrow(
            exception
        )

        return PokemonListRepository(service)
    }

    private suspend fun mockSuccessfulCase(): PokemonListRepository {
        whenever(service.getPokemonList()).thenReturn(pokemonResponse)

        return PokemonListRepository(service)
    }
}