package com.example.mypokedex.data.model.secondarymodels

import java.io.Serializable

data class BaseModel(
    val name: String,
    val url: String
) : Serializable
