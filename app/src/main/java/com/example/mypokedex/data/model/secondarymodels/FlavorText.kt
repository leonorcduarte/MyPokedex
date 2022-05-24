package com.example.mypokedex.data.model.secondarymodels

import java.io.Serializable

data class FlavorText(
    val flavor_text: String,
    val language: BaseModel,
    val version: BaseModel
): Serializable
