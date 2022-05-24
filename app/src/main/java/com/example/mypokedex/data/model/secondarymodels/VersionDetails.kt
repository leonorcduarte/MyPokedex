package com.example.mypokedex.data.model.secondarymodels

import java.io.Serializable

data class VersionDetails(
    val rarity: Int,
    val version: BaseModel
): Serializable
