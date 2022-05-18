package com.example.mypokedex.data.model.secondarymodels

import java.io.Serializable

data class MoveDetails(
    val move: BaseModel,
    val version_group_details: List<VersionGroupDetails>
): Serializable
