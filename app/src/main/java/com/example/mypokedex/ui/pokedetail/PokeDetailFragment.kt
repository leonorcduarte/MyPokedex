package com.example.mypokedex.ui.pokedetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.mypokedex.R
import com.example.mypokedex.data.model.mainmodels.Pokemon
import com.example.mypokedex.data.model.mainmodels.PokemonSpecies
import com.example.mypokedex.databinding.PokeDetailFragmentLayoutBinding
import com.example.mypokedex.util.StringUtils
import dagger.hilt.android.AndroidEntryPoint

//@AndroidEntryPoint
class PokeDetailFragment: Fragment() {

    private lateinit var binding: PokeDetailFragmentLayoutBinding

    private lateinit var pokemon: Pokemon
    private lateinit var pokemonSpecies: PokemonSpecies

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.poke_detail_fragment_layout, container, false)

        initScreen()

        return binding.root
    }

    private fun initScreen() {
        arguments?.getSerializable("pokemonDetail")?.let {
            pokemon = it as Pokemon
        }

        arguments?.getSerializable("pokemonSpecies")?.let {
            pokemonSpecies = it as PokemonSpecies
        }

        setPokemonBaseInfo()
    }

    private fun setPokemonBaseInfo(){
        binding.apply {
            pokeName.text = pokemon.name
            pokeId.text = context?.resources?.getString(R.string.id_code, StringUtils.getFormattedId(pokemon.id.toString()))
            pokeHeight.text = context?.resources?.getString(R.string.height_label, pokemon.height)
            pokeWeight.text = context?.resources?.getString(R.string.weight_label, pokemon.weight.toString())

            context?.let { Glide.with(it).load(pokemon.sprites.front_default).into(pokeImage) }
        }
    }
}