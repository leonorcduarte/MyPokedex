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
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.SCROLL_STATE_IDLE
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
    private var firsLoading = true

    private var adapter: PokemonListAdapter? = null
    private var pokemonList: MutableList<BaseModel> = mutableListOf()

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

        initAdapter()

        viewModel.getPokemonList(limit, offset)

        pokemonListObservers()

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

                if (newState == SCROLL_STATE_IDLE && lastPositionVisible == pokemonList.size - 1)
                    getPokemonList()
            }
        })
    }

    private fun getPokemonList() {
        offset += limit
        viewModel.getPokemonList(limit, offset)
    }

    private fun initAdapter() {
        binding.pokemonList.visibility = View.GONE
        binding.pokemonList.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        adapter = context?.let { PokemonListAdapter(pokemonList, this, it) }
        binding.pokemonList.adapter = adapter
    }

    private fun updateAdapter(pokemons: PokemonResponse?){
        if (pokemons != null) {
            pokemonList.addAll(pokemons.results)
            adapter?.updateList(pokemonList, offset)
        }
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
                    updateAdapter(resource.data)
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
                        adapter?.updateListItem(pokemonList, adapterPosition)
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
        if (isDisplayed && firsLoading){
            firsLoading = false
            binding.loading.visibility = View.VISIBLE
            binding.pokemonList.visibility = View.GONE
        }else{
            binding.loading.visibility = View.GONE
            binding.dialog.visibility = View.VISIBLE
            binding.pokemonList.visibility = View.VISIBLE
        }
    }

    private fun displayError(message: String?){
        if (message != null){
            TODO("")
        } else{
            TODO(" text.text = Unknown error")
        }

    }
}