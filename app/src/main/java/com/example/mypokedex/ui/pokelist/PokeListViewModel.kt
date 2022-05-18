package com.example.mypokedex.ui.pokelist

import androidx.lifecycle.*
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

    private val _resource: MutableLiveData<Resource<PokemonResponse>> = MutableLiveData()
    val pokemonList: LiveData<Resource<PokemonResponse>>
        get() = _resource

    fun getPokemonList(limit: Int, offset: Int) {
        viewModelScope.launch {
            pokemonListRepository.getPokemonList(limit, offset).onEach { resource ->
                _resource.value = resource
            }
                .launchIn(viewModelScope)
        }
    }
}