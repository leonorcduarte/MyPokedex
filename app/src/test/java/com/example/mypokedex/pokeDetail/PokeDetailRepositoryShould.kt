package com.example.mypokedex.pokeDetail

import com.example.mypokedex.data.model.mainmodels.EvolutionChain
import com.example.mypokedex.network.ApiService
import com.example.mypokedex.repository.PokemonDetailRepository
import com.example.mypokedex.utils.BaseUnitTest
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test
import java.lang.RuntimeException

class PokeDetailRepositoryShould : BaseUnitTest() {

    private val id = 1
    private val service: ApiService = mock()
    private val evolutionChain = mock<EvolutionChain>()
    private val exception = RuntimeException("Error occurred")

    @Test
    fun getPokemonEvolutionChainFromService(): Unit = runBlocking {
        val repository = PokemonDetailRepository(service)

        repository.getPokemonEvolutionChain(id).toList()[1]

        verify(service, times(1)).getPokemonEvolutionChain(id)
    }

    @Test
    fun emitPokemonEvolutionChainFromService(): Unit = runBlocking {
        val repository = mockSuccessfulCase()

        Assert.assertEquals(evolutionChain, repository.getPokemonEvolutionChain(id).toList()[1].data)
    }

    @Test
    fun emitErrorWhenServiceFails(): Unit = runBlocking {
        val repository = mockErrorCase()

        Assert.assertEquals("Error occurred", repository.getPokemonEvolutionChain(id).toList().last().message)
    }

    private suspend fun mockErrorCase(): PokemonDetailRepository {
        whenever(service.getPokemonEvolutionChain(id)).thenThrow(exception)

        return PokemonDetailRepository(service)
    }

    private suspend fun mockSuccessfulCase(): PokemonDetailRepository {
        whenever(service.getPokemonEvolutionChain(id)).thenReturn(evolutionChain)

        return PokemonDetailRepository(service)
    }


}