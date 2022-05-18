package com.example.mypokedex.ui.pokelist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mypokedex.R
import com.example.mypokedex.data.model.mainmodels.PokemonResponse
import com.example.mypokedex.data.model.secondarymodels.BaseModel
import com.example.mypokedex.databinding.PokeListFragmentLayoutBinding
import com.example.mypokedex.ui.pokelist.adapters.PokemonListAdapter
import com.example.mypokedex.util.Status
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PokeListFragment : Fragment(), PokemonListAdapter.OnItemClickListener {

    private lateinit var binding: PokeListFragmentLayoutBinding
    private lateinit var viewModel: PokeListViewModel

    private var limit = 10
    private var offset = 0
    private var adapterPosition = 0

    private var adapter: PokemonListAdapter? = null
    private lateinit var pokemonList: List<BaseModel>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.poke_list_fragment_layout, container, false)

        initScreen()
        return binding.root
    }

    private fun initScreen() {
        viewModel = ViewModelProvider(this)[PokeListViewModel::class.java]

        viewModel.getPokemonList(limit, offset)

        pokemonListObservers()
    }

    private fun initAdapter(pokemons: PokemonResponse?) {
        if (pokemons != null) {
            pokemonList = pokemons.results
        }
        binding.pokemonList.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        adapter = context?.let { PokemonListAdapter(pokemonList, this, it) }
        binding.pokemonList.adapter = adapter
    }

    override fun onPokeBallClick(position: Int, pokemonName: String) {
        adapterPosition = position
        viewModel.getPokemonByName(pokemonName)

        pokemonDetailObservers()
    }

    private fun pokemonListObservers() {
        viewModel.pokemonList.observe(viewLifecycleOwner, Observer { resource ->
            when(resource.status){
                Status.SUCCESS -> {
                    displayLoading(false)
                    initAdapter(resource.data)
                }
                Status.ERROR -> {
                    displayLoading(false)
                    displayError(resource.message)
                }
                Status.LOADING -> {
                    displayLoading(true)
                }
            }
        })
    }

    private fun pokemonDetailObservers() {
        viewModel.pokemonDetail.observe(viewLifecycleOwner, Observer { resource ->
            when(resource.status){
                Status.SUCCESS -> {
                    resource.data?.sprites?.let {
                        pokemonList[adapterPosition].image = it.front_default
                        adapter?.setList(pokemonList, adapterPosition)
                    }
                }
                Status.ERROR -> {
                    displayError(resource.message)
                }
                Status.LOADING -> {
                }
            }
        })
    }

    private fun displayLoading(isDisplayed: Boolean){
        binding.loading.visibility = if (isDisplayed) View.VISIBLE else View.GONE
    }

    private fun displayError(message: String?){
        if (message != null){
            TODO("")
        } else{
            TODO(" text.text = Unknown error")
        }

    }
}