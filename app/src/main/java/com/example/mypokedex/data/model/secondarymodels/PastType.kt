package com.example.mypokedex.data.model.secondarymodels

import java.io.Serializable

data class PastType(
    val generation: BaseModel,
    val type: Type
): Serializable
