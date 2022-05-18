package com.example.mypokedex.data.model.secondarymodels

import java.io.Serializable

data class GameIndex(
    val game_index: Int,
    val version: BaseModel
) : Serializable
