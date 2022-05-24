package com.example.mypokedex.data.model.mainmodels

import com.example.mypokedex.data.model.secondarymodels.*
import java.io.Serializable

data class Pokemon(
    val id: Int,
    val name: String,
    val base_experience: String,
    val height: String,
    val is_default: Boolean,
    val order: Int,
    val weight: Int,
    val abilities: List<Abilities>,
    val forms: List<BaseModel>,
    val game_indices: List<GameIndex>,
    val held_items: List<HeldItems>,
    val location_area_encounters: String,
    val moves: List<MoveDetails>,
    val species: BaseModel,
    val stats: List<Stat>,
    val types: List<Type>,
    val past_types: List<PastType>,
    val sprites: Sprites
) : Serializable
