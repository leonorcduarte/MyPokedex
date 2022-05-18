package com.example.mypokedex.data.model.secondarymodels

import java.io.Serializable

data class HeldItems(
    val item: BaseModel,
    val version_details: List<VersionDetails>
): Serializable
