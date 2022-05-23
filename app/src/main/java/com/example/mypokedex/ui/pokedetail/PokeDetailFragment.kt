package com.example.mypokedex.ui.pokedetail

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.mypokedex.R
import com.example.mypokedex.data.model.mainmodels.EvolutionChain
import com.example.mypokedex.data.model.mainmodels.Pokemon
import com.example.mypokedex.data.model.mainmodels.PokemonSpecies
import com.example.mypokedex.data.model.secondarymodels.Abilities
import com.example.mypokedex.data.model.secondarymodels.FlavorText
import com.example.mypokedex.data.model.secondarymodels.Type
import com.example.mypokedex.databinding.PokeDetailFragmentLayoutBinding
import com.example.mypokedex.ui.pokedetail.adapters.EvolutionChainAdapter
import com.example.mypokedex.util.Constants
import com.example.mypokedex.util.PokemonColorUtils
import com.example.mypokedex.util.Status
import com.example.mypokedex.util.StringUtils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PokeDetailFragment: Fragment() {

    private lateinit var binding: PokeDetailFragmentLayoutBinding
    private lateinit var viewModel: PokeDetailViewModel

    private lateinit var adapter: EvolutionChainAdapter

    private lateinit var pokemon: Pokemon
    private lateinit var pokemonSpecies: PokemonSpecies

    private var colorPairList = ArrayList<Pair<String, List<Int?>>>()
    private var typeColorPairList = ArrayList<Pair<String, Int?>>()
    private lateinit var backgroundColorPair: Pair<String, List<Int?>>
    private val evolutionChainList = arrayListOf<String>()

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
        viewModel = ViewModelProvider(this)[PokeDetailViewModel::class.java]

        arguments?.getSerializable(Constants.POKEMON_DETAIL)?.let {
            pokemon = it as Pokemon
        }

        arguments?.getSerializable(Constants.POKEMON_SPECIES)?.let {
            pokemonSpecies = it as PokemonSpecies
        }

        colorPairList = PokemonColorUtils.getBackgroundColors(activity)
        typeColorPairList = PokemonColorUtils.getTypeColors(activity)

        setBackgroundColor()
        setListeners()
        initEvolutionAdapter()
        populateViews()
    }

    private fun getEvolutionChainId() =
        StringUtils.getSubstring(pokemonSpecies.evolution_chain.url, "/", 6).toInt()


    private fun populateViews() {
        setPokemonFlavorEntry()
        setPokemonBaseInfo()
        setPokemonTypes()
        setPokemonAbilities()
        checkEvolutionChainStatus()
    }

    private fun checkEvolutionChainStatus() {
        if(viewModel.isEvolutionExpanded){
            binding.evolutionChainList.visibility = View.VISIBLE
            binding.arrow.rotation = 180F
            setEvolutionChain(viewModel.pokemonEvolutionChain.value?.data)
            updateEvolutionAdapter()
        }

    }

    private fun setPokemonTypes() {
        setTypesVisibility()
        binding.apply {
            for(type: Type in pokemon.types) {
                for (colorPair: Pair<String, Int?> in typeColorPairList) {
                    when (type.slot) {
                        1 -> {
                            type1.text = type.type.name
                            if (type.type.name == colorPair.first)
                                colorPair.second?.let { type1Continer.background.setTint(it) }
                        }
                        2 -> {
                            type2.text = type.type.name
                            if (type.type.name == colorPair.first)
                                colorPair.second?.let { type2Continer.background.setTint(it) }
                        }
                    }

                }
            }
        }
    }

    private fun setTypesVisibility() {
        if (pokemon.types.size == 1) {
            binding.typesTitle.text = context?.resources?.getString(R.string.type_title)
            binding.verticalSeparator1.visibility = View.GONE
            binding.type2Continer.visibility = View.GONE
        } else
            binding.typesTitle.text = context?.resources?.getString(R.string.types_title)
    }

    private fun setListeners() {
        binding.evolutionChainContainer.setOnClickListener { expandEvolutionChain() }
    }

    private fun expandEvolutionChain() {
        when(viewModel.isEvolutionExpanded){
            false -> {
                if (!viewModel.infoAlreadyExists){
                    viewModel.getPokemonEvolutionChain(getEvolutionChainId())
                    pokemonEvolutionChainObservers()
                }
                binding.evolutionChainList.visibility = View.VISIBLE
                binding.arrow.rotation = 180F
                viewModel.isEvolutionExpanded = true
            }
            true -> {
                binding.evolutionChainList.visibility = View.GONE
                binding.arrow.rotation = 0F
                viewModel.isEvolutionExpanded = false
            }
        }
    }

    private fun initEvolutionAdapter() {
        binding.evolutionChainList.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        adapter = EvolutionChainAdapter(evolutionChainList, backgroundColorPair)
        binding.evolutionChainList.adapter = adapter
    }

    private fun updateEvolutionAdapter(){
        if (evolutionChainList.isNotEmpty())
            adapter.setEvolutionChain(evolutionChainList)
    }

    private fun pokemonEvolutionChainObservers() {
       viewModel.pokemonEvolutionChain.observe(viewLifecycleOwner, Observer { resource ->
           when(resource.status){
               Status.SUCCESS -> {
                   displayLoading(true)
                   viewModel.infoAlreadyExists = true
                   setEvolutionChain(resource.data)
                   updateEvolutionAdapter()
               }
               Status.ERROR -> {
                   displayLoading(true)
               }
               Status.LOADING -> {
                   displayLoading(false)
               }
           }
       })
    }

    private fun setEvolutionChain(evolutionChain: EvolutionChain?) {
        if (evolutionChain != null){
            evolutionChainList.add(evolutionChain.chain.species.name)
            evolutionChainList.add(evolutionChain.chain.evolves_to[0].species.name)
            if (evolutionChain.chain.evolves_to[0].evolves_to.isNotEmpty())
                evolutionChainList.add(evolutionChain.chain.evolves_to[0].evolves_to[0].species.name)
        }
    }

    private fun setBackgroundColor() {
        for (colorPair: Pair<String, List<Int?>> in colorPairList){
            if(pokemonSpecies.color.name == colorPair.first){
                backgroundColorPair = colorPair
                colorPair.second[0]?.let { binding.mainLayout.setBackgroundColor(it) }
                colorPair.second[1]?.let { binding.flavorContainer.background.setTint(it) }
            }
        }
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

    private fun displayLoading(isDisplayed: Boolean){
        binding.loading.visibility = if(isDisplayed) View.GONE else View.VISIBLE
    }
}