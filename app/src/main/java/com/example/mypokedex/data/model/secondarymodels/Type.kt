package com.example.mypokedex.data.model.secondarymodels

import java.io.Serializable

data class Type(
    val slot: Int,
    val type: BaseModel
): Serializable
