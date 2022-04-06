package com.example.ilm_izlab.model

import java.io.Serializable

data class
RegionsModel(
    val id: Int,
    val name_uz: String,
    val created_at: String,
    val districts: List<DistrictsModel>,
    var isBtnVisibility: Boolean = false
): Serializable

