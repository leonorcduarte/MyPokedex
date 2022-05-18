package com.example.mypokedex.data.model.mainmodels

import com.example.mypokedex.data.model.secondarymodels.BaseModel

data class PokemonResponse(
    val count: Int,
    val next: String,
    val previous: String?,
    val results: List<BaseModel>
)
