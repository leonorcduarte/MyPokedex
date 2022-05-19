package com.example.mypokedex.data.model.mainmodels

import com.example.mypokedex.data.model.secondarymodels.BaseModel
import com.example.mypokedex.data.model.secondarymodels.FlavorText
import com.example.mypokedex.data.model.secondarymodels.Url
import java.io.Serializable

data class PokemonSpecies(
    val base_happiness: Int,
    val capture_rate: Int,
    val color: BaseModel,
    val egg_groups: List<BaseModel>,
    val evolution_chain: Url,
    val flavor_text_entries: List<FlavorText>,
    val generation: BaseModel,
    val growth_rate: BaseModel,
    val habitat: BaseModel,
    val id: Int,
    val name: String,
    val is_baby: Boolean,
    val is_legendary: Boolean,
    val is_mythical: Boolean,
    val shape: BaseModel
) : Serializable