package com.example.mypokedex.pokeList

import com.example.mypokedex.data.model.mainmodels.PokemonResponse
import com.example.mypokedex.repository.PokemonDetailRepository
import com.example.mypokedex.repository.PokemonListRepository
import com.example.mypokedex.ui.pokelist.PokeListViewModel
import com.example.mypokedex.utils.getValueForTest
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.Test

import org.junit.Assert.*

import com.example.mypokedex.util.Resource
import com.example.mypokedex.utils.BaseUnitTest

class PokeListViewModelShould : BaseUnitTest(){

    private val repository: PokemonListRepository = mock()
    private val detailRepository: PokemonDetailRepository = mock()
    private val pokemonResponseList = mock<PokemonResponse>()
    private val expected = Resource.success(pokemonResponseList)
    private val exception = Resource.error(pokemonResponseList, "Something went wrong")

    @Test
    fun getPokemonResponseFromRepository(): Unit = runBlocking{
        val viewModel = mockSuccessfulCase()
        viewModel.pokemonResponseList.getValueForTest()

        verify(repository, times(1)).getPokemonList()
    }

    @Test
    fun emitPokemonResponseFromRepository(): Unit = runBlocking {

        val viewModel = mockSuccessfulCase()
        viewModel.pokemonResponseList.getValueForTest()

        assertEquals(expected, viewModel.pokemonResponseList.getValueForTest())
    }

    @Test
    fun emitErrorWhenReceiveError(){
        runBlocking {
            whenever(repository.getPokemonList()).thenReturn(
                flow {
                    emit(exception)
                }
            )
        }
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
