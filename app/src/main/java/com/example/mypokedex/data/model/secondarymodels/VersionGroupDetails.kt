package com.example.mypokedex.data.model.secondarymodels

import java.io.Serializable

data class VersionGroupDetails(
    val level_learned_at: Int,
    val version_group: BaseModel,
    val move_learn_method: BaseModel
): Serializable
