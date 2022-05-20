package com.example.mypokedex.data.model.mainmodels

import com.example.mypokedex.data.model.secondarymodels.BaseModel

data class Evolution(
    val is_baby: Boolean,
    val species: BaseModel,
    val evolves_to: List<Evolution>
)
