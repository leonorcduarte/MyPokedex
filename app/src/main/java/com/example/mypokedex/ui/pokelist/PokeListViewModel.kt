package com.example.mypokedex.ui.pokelist

import androidx.lifecycle.*
import com.example.mypokedex.data.model.mainmodels.Pokemon
import com.example.mypokedex.data.model.mainmodels.PokemonResponse
import com.example.mypokedex.data.model.mainmodels.PokemonSpecies
import com.example.mypokedex.data.model.secondarymodels.BaseModel
import com.example.mypokedex.repository.PokemonDetailRepository
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
    private val pokemonListRepository: PokemonListRepository,
    private val pokemonDetailRepository: PokemonDetailRepository
): ViewModel() {

    var firsLoading = true
    var limit = 10
    var offset = 0

    var pokemonList: MutableList<BaseModel> = mutableListOf()

    var loader = MutableLiveData<Boolean>()

    private val _pokemonResponse: MutableLiveData<Resource<PokemonResponse>> = MutableLiveData()
    val pokemonResponseList: LiveData<Resource<PokemonResponse>>
        get() = _pokemonResponse

    private val _pokemonDetail: MutableLiveData<Resource<Pokemon>> = MutableLiveData()
    val pokemonDetail: LiveData<Resource<Pokemon>>
        get() = _pokemonDetail

    private val _pokemonSpecies: MutableLiveData<Resource<PokemonSpecies>> = MutableLiveData()
    val pokemonSpecies: LiveData<Resource<PokemonSpecies>>
        get() = _pokemonSpecies

    fun getPokemonList(limit: Int = 10, offset: Int = 0) {
        loader.postValue(true)
        viewModelScope.launch {
            pokemonListRepository.getPokemonList(limit, offset).onEach { resource ->
                loader.postValue(false)
                _pokemonResponse.value = resource
            }
                .launchIn(viewModelScope)
        }
    }

    fun getPokemonByName(name: String){
        viewModelScope.launch {
            pokemonDetailRepository.getPokemonByName(name).onEach { resource ->
                _pokemonDetail.value = resource
            }
                .launchIn(viewModelScope)
        }
    }

    fun getPokemonSpecies(id: Int){
        viewModelScope.launch {
            pokemonDetailRepository.getPokemonSpecies(id).onEach { resource ->
                _pokemonSpecies.value = resource
            }
                .launchIn(viewModelScope)
        }
    }
}