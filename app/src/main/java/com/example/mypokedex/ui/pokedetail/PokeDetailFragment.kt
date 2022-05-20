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
import com.example.mypokedex.data.model.secondarymodels.BaseModel
import com.example.mypokedex.data.model.secondarymodels.FlavorText
import com.example.mypokedex.databinding.PokeDetailFragmentLayoutBinding
import com.example.mypokedex.util.StringUtils

//@AndroidEntryPoint
class PokeDetailFragment: Fragment() {

    private lateinit var binding: PokeDetailFragmentLayoutBinding

    private lateinit var pokemon: Pokemon
    private lateinit var pokemonSpecies: PokemonSpecies
    private val colorPairList = ArrayList<Pair<String, List<Int?>>>()

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
        prepareBackgroundColors()
        arguments?.getSerializable("pokemonDetail")?.let {
            pokemon = it as Pokemon
        }

        arguments?.getSerializable("pokemonSpecies")?.let {
            pokemonSpecies = it as PokemonSpecies
        }

        setBackgroundColor()
        setPokemonFlavorEntry()
        setPokemonBaseInfo()
        setPokemonAbilities()
    }

    private fun setBackgroundColor() {
        for (colorPair: Pair<String, List<Int?>> in colorPairList){
            if(pokemonSpecies.color.name == colorPair.first){
                colorPair.second[0]?.let { binding.mainLayout.setBackgroundColor(it) }
                colorPair.second[1]?.let { binding.flavorContainer.background.setTint(it) }
            }

        }


    }

    private fun prepareBackgroundColors() {
        colorPairList.add(Pair("black",
            listOf(context?.resources?.getColor(R.color.primary_black_pokemon, activity?.theme),
                context?.resources?.getColor(R.color.black, activity?.theme))))
        colorPairList.add(Pair("blue",
            listOf(context?.resources?.getColor(R.color.primary_blue_pokemon, activity?.theme),
                context?.resources?.getColor(R.color.secondary_blue_pokemon, activity?.theme))))
        colorPairList.add(Pair("brown",
            listOf(context?.resources?.getColor(R.color.primary_brown_pokemon, activity?.theme),
                context?.resources?.getColor(R.color.secondary_brown_pokemon, activity?.theme))))
        colorPairList.add(Pair("gray",
            listOf(context?.resources?.getColor(R.color.primary_gray_pokemon, activity?.theme),
                context?.resources?.getColor(R.color.secondary_gray_pokemon, activity?.theme))))
        colorPairList.add(Pair("green",
            listOf(context?.resources?.getColor(R.color.primary_green_pokemon, activity?.theme),
                context?.resources?.getColor(R.color.secondary_green_pokemon, activity?.theme))))
        colorPairList.add(Pair("pink",
            listOf(context?.resources?.getColor(R.color.primary_pink_pokemon, activity?.theme),
                context?.resources?.getColor(R.color.secondary_pink_pokemon, activity?.theme))))
        colorPairList.add(Pair("purple",
            listOf(context?.resources?.getColor(R.color.primary_purple_pokemon, activity?.theme),
                context?.resources?.getColor(R.color.secondary_purple_pokemon, activity?.theme))))
        colorPairList.add(Pair("red",
            listOf(context?.resources?.getColor(R.color.primary_red_pokemon, activity?.theme),
                context?.resources?.getColor(R.color.secondary_red_pokemon, activity?.theme))))
        colorPairList.add(Pair("yellow",
            listOf(context?.resources?.getColor(R.color.primary_yellow_pokemon, activity?.theme),
                context?.resources?.getColor(R.color.secondary_yellow_pokemon, activity?.theme))))
        colorPairList.add(Pair("white",
            listOf(context?.resources?.getColor(R.color.white, activity?.theme),
                context?.resources?.getColor(R.color.white, activity?.theme))))
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