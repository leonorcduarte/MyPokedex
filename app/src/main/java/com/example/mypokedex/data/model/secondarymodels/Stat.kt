package com.example.mypokedex.data.model.secondarymodels

import java.io.Serializable

data class Stat(
    val base_stat: Int,
    val effort: Int,
    val stat: BaseModel
): Serializable
