package com.example.mypokedex.ui.pokelist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.mypokedex.R
import com.example.mypokedex.data.model.mainmodels.PokemonResponse
import com.example.mypokedex.databinding.PokeListFragmentLayoutBinding
import com.example.mypokedex.util.Resource
import com.example.mypokedex.util.Status
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PokeListFragment : Fragment() {

    private lateinit var binding: PokeListFragmentLayoutBinding
    private lateinit var viewModel: PokeListViewModel

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

        viewModel.getPokemonList(10, 0)

        pokemonListObservers()
    }

    private fun pokemonListObservers() {
        viewModel.pokemonList.observe(viewLifecycleOwner, Observer { resource ->
            when(resource.status){
                Status.SUCCESS -> {
                    displayLoading(false)

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