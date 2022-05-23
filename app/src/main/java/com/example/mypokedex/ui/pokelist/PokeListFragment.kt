package com.example.mypokedex.ui.pokelist

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.SCROLL_STATE_IDLE
import com.example.mypokedex.R
import com.example.mypokedex.data.model.mainmodels.Pokemon
import com.example.mypokedex.data.model.mainmodels.PokemonResponse
import com.example.mypokedex.data.model.mainmodels.PokemonSpecies
import com.example.mypokedex.data.model.secondarymodels.BaseModel
import com.example.mypokedex.databinding.PokeListFragmentLayoutBinding
import com.example.mypokedex.ui.pokelist.adapters.PokemonListAdapter
import com.example.mypokedex.util.Constants
import com.example.mypokedex.util.Status
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PokeListFragment : Fragment(), PokemonListAdapter.OnItemClickListener {

    private lateinit var binding: PokeListFragmentLayoutBinding
    private lateinit var viewModel: PokeListViewModel

    //private var limit = 10
    //private var offset = 0
    private var adapterPosition = 0
    private var goToDetail = false
    private var updateList = false

    private var adapter: PokemonListAdapter? = null
    //private var pokemonList: MutableList<BaseModel> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.poke_list_fragment_layout, container, false)

        initScreen()
        return binding.root
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        Toast.makeText(activity, "onConfigurationChanged", Toast.LENGTH_SHORT).show()
    }

    private fun initScreen() {
        viewModel = ViewModelProvider(this)[PokeListViewModel::class.java]

        initAdapter()

        if (viewModel.firsLoading) {

            viewModel.getPokemonList(viewModel.limit, viewModel.offset)
            viewModel.firsLoading = false
            pokemonListObservers()
        } else{
            updateAdapter(viewModel.pokemonResponseList.value?.data)
            listVisibility(false)
        }

        setScrollListener()
    }

    private fun setScrollListener() {
        var lastPositionVisible = 0
        binding.pokemonList.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)

                val layoutManager = recyclerView.layoutManager
                if (layoutManager is LinearLayoutManager)
                    lastPositionVisible = layoutManager.findLastVisibleItemPosition()

                if (newState == SCROLL_STATE_IDLE && lastPositionVisible == viewModel.pokemonList.size - 1)
                    getPokemonList()
            }
        })
    }

    private fun getPokemonList() {
        viewModel.offset += viewModel.limit
        updateList = true
        viewModel.getPokemonList(viewModel.limit, viewModel.offset)
    }

    private fun initAdapter() {
        binding.pokemonList.visibility = View.GONE
        if(resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE)
            binding.pokemonList.layoutManager = GridLayoutManager(activity, 2)
        else
            binding.pokemonList.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        adapter = context?.let { PokemonListAdapter(viewModel.pokemonList, this, it) }
        binding.pokemonList.adapter = adapter
    }

    private fun updateAdapter(pokemons: PokemonResponse?){
        if (pokemons != null) {
            if (viewModel.offset > 0)
                viewModel.pokemonList.addAll(pokemons.results)
            else
                viewModel.pokemonList = pokemons.results as MutableList<BaseModel>
            adapter?.updateList(viewModel.pokemonList, viewModel.offset)
        }
    }

    override fun onPokeBallClick(position: Int, pokemonName: String) {
        adapterPosition = position
        viewModel.getPokemonByName(pokemonName)

        pokemonDetailObservers()
    }

    override fun onPokemonClick(position: Int, pokemonName: String) {
        goToDetail = true

        viewModel.getPokemonByName(pokemonName)
        pokemonDetailObservers()
    }

    private fun openDetail(pokemon: Pokemon?, species: PokemonSpecies?) {
        goToDetail = false
        val bundle: Bundle = bundleOf()
        bundle.putSerializable(Constants.POKEMON_DETAIL, pokemon)
        bundle.putSerializable(Constants.POKEMON_SPECIES, species)
        if (pokemon != null){
            Navigation.findNavController(binding.root).navigate(R.id.pokeDetailFragment, bundle)
        }
    }

    private fun pokemonListObservers() {
        viewModel.pokemonResponseList.observe(viewLifecycleOwner, Observer { resource ->
            when(resource.status){
                Status.SUCCESS -> {
                    displayLoading(true)
                    listVisibility(false)
                    updateAdapter(resource.data)
                }
                Status.ERROR -> {
                    displayLoading(true)
                    listVisibility(true)
                    displayError(resource.message)
                }
                Status.LOADING -> {
                    displayLoading(false)
                }
            }
        })
    }

    private fun pokemonDetailObservers() {
        viewModel.pokemonDetail.observe(viewLifecycleOwner, Observer { resource ->
            when(resource.status){
                Status.SUCCESS -> {
                    if (goToDetail){
                        listVisibility(true)
                        resource.data?.id?.let { viewModel.getPokemonSpecies(it) }
                        pokemonSpeciesObservers(resource.data)
                    } else {
                        resource.data?.sprites?.let {
                            viewModel.pokemonList[adapterPosition].image = it.front_default
                            adapter?.updateListItem(viewModel.pokemonList, adapterPosition)
                        }
                    }
                }
                Status.ERROR -> {
                    listVisibility(false)
                    displayError(resource.message)
                }
                Status.LOADING -> {
                    if (goToDetail) {
                        listVisibility(true)
                        displayLoading(false)
                    }
                }
            }
        })
    }

    private fun pokemonSpeciesObservers(pokemon: Pokemon?) {
        viewModel.pokemonSpecies.observe(viewLifecycleOwner, Observer { resource ->
            when(resource.status){
                Status.SUCCESS -> {
                    displayLoading(true)
                    openDetail(pokemon, resource?.data)
                }
                Status.ERROR -> {
                    displayError(resource.message)
                }
                Status.LOADING -> {
                    displayLoading(false)
                }
            }
        })
    }

    private fun displayLoading(isDisplayed: Boolean){
        if (isDisplayed)
            binding.loading.visibility = View.GONE
        else if(!updateList)
            binding.loading.visibility = View.VISIBLE
    }

    private fun listVisibility(hide: Boolean){
        binding.pokemonList.visibility = if (hide) View.GONE else View.VISIBLE
        binding.dialog.visibility = if (hide) View.GONE else View.VISIBLE
    }

    private fun displayError(message: String?){
        if (message != null){
            Toast.makeText(context, message, Toast.LENGTH_LONG).show()
        } else{
            TODO(" text.text = Unknown error")
        }

    }
}