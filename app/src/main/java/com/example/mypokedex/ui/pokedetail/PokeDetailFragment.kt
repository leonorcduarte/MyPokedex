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
import com.example.mypokedex.data.model.secondarymodels.Abilities
import com.example.mypokedex.data.model.secondarymodels.FlavorText
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

        setPokemonFlavorEntry()
        setPokemonBaseInfo()
        setPokemonAbilities()
    }

    private fun setPokemonAbilities() {
        var hasHiddenAbility = false
        var hasTwoMainAbilities = false
        binding.apply {
            for(ability: Abilities in pokemon.abilities){
                when(ability.slot){
                    1 -> ability1.text = ability.ability.name
                    2 -> {
                        ability2.text = ability.ability.name
                        hasTwoMainAbilities = true
                    }
                    3 -> {
                        hiddenAbility.text = context?.resources?.getString(R.string.hidden_ability_label, ability.ability.name)
                        hasHiddenAbility = true
                    }
                }
            }
        }

        setAbilitiesVisibility(hasTwoMainAbilities, hasHiddenAbility)
    }

    private fun setAbilitiesVisibility(hasTwoMainAbilities: Boolean, hasHiddenAbility: Boolean) {
        if(!hasTwoMainAbilities) {
            binding.verticalSeparator.visibility = View.INVISIBLE
            binding.ability2.visibility = View.INVISIBLE
        }
        if (!hasHiddenAbility){
            binding.horizontalSeparator.visibility = View.GONE
            binding.hiddenAbility.visibility = View.GONE
        }
    }

    private fun setPokemonFlavorEntry() {
        val flavorDesc: String
        for (flavor: FlavorText in pokemonSpecies.flavor_text_entries){
            if (flavor.version.name == "red"){
                flavorDesc = flavor.flavor_text
                binding.flavorText.text = flavorDesc.replace("\n", " ").replace("\\f", "\n")
                return
            }
        }
    }

    private fun setPokemonBaseInfo(){
        binding.apply {
            pokeId.text = context?.resources?.getString(R.string.id_code, StringUtils.getFormattedId(pokemon.id.toString()))
            pokeName.text = pokemon.name
            pokeShape.text = pokemonSpecies.shape.name
            pokeHeight.text = context?.resources?.getString(R.string.height_label, pokemon.height)
            pokeWeight.text = context?.resources?.getString(R.string.weight_label, pokemon.weight.toString())

            context?.let { Glide.with(it).load(pokemon.sprites.front_default).into(pokeImage) }
        }
    }
}