package com.example.mypokedex.ui.pokedetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mypokedex.data.model.mainmodels.EvolutionChain
import com.example.mypokedex.data.model.mainmodels.PokemonResponse
import com.example.mypokedex.repository.PokemonDetailRepository
import com.example.mypokedex.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokeDetailViewModel
@Inject constructor(
    private val pokemonDetailRepository: PokemonDetailRepository
): ViewModel() {

    var isEvolutionExpanded = false
    var infoAlreadyExists = false

    var evolutionChainList = arrayListOf<String>()

    private val _pokemonEvolutionChain: MutableLiveData<Resource<EvolutionChain>> = MutableLiveData()
    val pokemonEvolutionChain: LiveData<Resource<EvolutionChain>>
        get() = _pokemonEvolutionChain

    fun getPokemonEvolutionChain(id: Int){
        viewModelScope.launch {
            pokemonDetailRepository.getPokemonEvolutionChain(id).onEach { resource ->
                _pokemonEvolutionChain.value = resource
            }
                .launchIn(viewModelScope)
        }
    }
}