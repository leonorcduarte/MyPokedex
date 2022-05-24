package com.example.mypokedex.data.model.secondarymodels

import java.io.Serializable

data class Abilities(
    val is_hidden: Boolean,
    val slot: Int,
    val ability: BaseModel
) : Serializable
