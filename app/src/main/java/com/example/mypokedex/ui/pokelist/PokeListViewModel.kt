package com.example.mypokedex.ui.pokelist

import androidx.lifecycle.*
import com.example.mypokedex.data.model.mainmodels.Pokemon
import com.example.mypokedex.data.model.mainmodels.PokemonResponse
import com.example.mypokedex.repository.PokemonListRepository
import com.example.mypokedex.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokeListViewModel
@Inject
constructor(
    private val pokemonListRepository: PokemonListRepository
): ViewModel() {

    private val _pokemonResponse: MutableLiveData<Resource<PokemonResponse>> = MutableLiveData()
    val pokemonList: LiveData<Resource<PokemonResponse>>
        get() = _pokemonResponse

    private val _pokemonDetail: MutableLiveData<Resource<Pokemon>> = MutableLiveData()
    val pokemonDetail: LiveData<Resource<Pokemon>>
        get() = _pokemonDetail

    fun getPokemonList(limit: Int, offset: Int) {
        viewModelScope.launch {
            pokemonListRepository.getPokemonList(limit, offset).onEach { resource ->
                _pokemonResponse.value = resource
            }
                .launchIn(viewModelScope)
        }
    }

    fun getPokemonByName(name: String){
        viewModelScope.launch {
            pokemonListRepository.getPokemonByName(name).onEach { resource ->
                _pokemonDetail.value = resource
            }
                .launchIn(viewModelScope)
        }
    }
}