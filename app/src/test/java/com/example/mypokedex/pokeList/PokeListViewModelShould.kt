package com.example.mypokedex.pokeList

import com.example.mypokedex.data.model.mainmodels.PokemonResponse
import com.example.mypokedex.repository.PokemonDetailRepository
import com.example.mypokedex.repository.PokemonListRepository
import com.example.mypokedex.ui.pokelist.PokeListViewModel
import com.example.mypokedex.utils.getValueForTest
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.Test

import org.junit.Assert.*

import com.example.mypokedex.util.Resource
import com.example.mypokedex.utils.BaseUnitTest
import com.example.mypokedex.utils.captureValues
import com.nhaarman.mockitokotlin2.mock

class PokeListViewModelShould : BaseUnitTest(){

    private val repository: PokemonListRepository = mock()
    private val detailRepository: PokemonDetailRepository = mock()
    private val pokemonResponse = mock<PokemonResponse>()
    private val expected = Resource.success(pokemonResponse)
    private val exception = Resource.error(pokemonResponse, "Error occurred")

    @Test
    fun getPokemonResponseFromRepository(): Unit = runBlocking{
        val viewModel = mockSuccessfulCase()
        viewModel.getPokemonList()

        verify(repository, times(1)).getPokemonList()
    }

    @Test
    fun emitPokemonResponseFromRepository(): Unit = runBlocking {
        val viewModel = mockSuccessfulCase()
        viewModel.getPokemonList()

        assertEquals(expected, viewModel.pokemonResponseList.getValueForTest())
    }

    @Test
    fun emitErrorWhenReceiveError(){
        val viewModel = mockErrorCase()

        viewModel.getPokemonList()

        assertEquals(exception, viewModel.pokemonResponseList.getValueForTest()!!)
    }

    @Test
    fun showLoaderWhileLoading(): Unit = runBlocking {
        val viewModel = mockSuccessfulCase()

        viewModel.loader.captureValues {
            viewModel.getPokemonList()

            assertEquals(true, values[0])
        }
    }

    @Test
    fun hideLoaderAfterPokemonListLoad(): Unit = runBlocking{
        val viewModel = mockSuccessfulCase()

        viewModel.loader.captureValues {
            viewModel.getPokemonList()

            assertEquals(false, values.last())
        }
    }

    @Test
    fun hideLoaderAfterError(): Unit = runBlocking {
        val viewModel = mockErrorCase()

        viewModel.loader.captureValues {
            viewModel.getPokemonList()

            assertEquals(false, values.last())
        }
    }

    private fun mockErrorCase(): PokeListViewModel {
        runBlocking {
            whenever(repository.getPokemonList()).thenReturn(
                flow {
                    emit(exception)
                }
            )
        }

        return PokeListViewModel(repository, detailRepository)
    }

    private fun mockSuccessfulCase(): PokeListViewModel {
        runBlocking {
            whenever(repository.getPokemonList()).thenReturn(
                flow {
                    emit(expected)
                }
            )
        }

        return PokeListViewModel(repository, detailRepository)
    }
}
