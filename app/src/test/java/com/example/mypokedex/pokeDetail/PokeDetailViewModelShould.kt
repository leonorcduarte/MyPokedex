package com.example.mypokedex.pokeDetail

import com.example.mypokedex.data.model.mainmodels.EvolutionChain
import com.example.mypokedex.data.model.mainmodels.PokemonResponse
import com.example.mypokedex.network.ApiService
import com.example.mypokedex.repository.PokemonDetailRepository
import com.example.mypokedex.ui.pokedetail.PokeDetailViewModel
import com.example.mypokedex.ui.pokelist.PokeListViewModel
import com.example.mypokedex.util.Resource
import com.example.mypokedex.utils.BaseUnitTest
import com.example.mypokedex.utils.getValueForTest
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test

class PokeDetailViewModelShould : BaseUnitTest() {

    private val id = 1
    private val service: ApiService = mock()
    private val repository: PokemonDetailRepository = mock()
    private val evolutionChainResponse = mock<EvolutionChain>()
    private val expected = Resource.success(evolutionChainResponse)
    private val exception = Resource.error(evolutionChainResponse, "Error occurred")

    @Test
    fun getPokemonEvolutionChainFromService(): Unit = runBlocking {
        val viewModel = mockSuccessfulCase()

        viewModel.getPokemonEvolutionChain(id)

        viewModel.pokemonEvolutionChain.getValueForTest()

        verify(repository, times(1)).getPokemonEvolutionChain(id)
    }

    @Test
    fun emitEvolutionChainResponseFromRepository(): Unit = runBlocking {
        val viewModel = mockSuccessfulCase()

        viewModel.getPokemonEvolutionChain(id)

        assertEquals(expected, viewModel.pokemonEvolutionChain.getValueForTest())
    }

    @Test
    fun emitErrorWhenReceiveError(){
        val viewModel = mockErrorCase()

        viewModel.getPokemonEvolutionChain(id)

        assertEquals(exception, viewModel.pokemonEvolutionChain.getValueForTest()!!)

    }

    private fun mockSuccessfulCase(): PokeDetailViewModel {
        runBlocking {
            whenever(repository.getPokemonEvolutionChain(id)).thenReturn(
                flow {
                    emit(expected)
                }
            )
        }

        return PokeDetailViewModel(repository)
    }

    private fun mockErrorCase(): PokeDetailViewModel {
        runBlocking {
            whenever(repository.getPokemonEvolutionChain(id)).thenReturn(
                flow {
                    emit(exception)
                }
            )
        }

        return PokeDetailViewModel(repository)
    }
}