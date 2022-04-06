package com.example.ilm_izlab.model

import java.io.Serializable

data class DistrictsModel(
    val id: Int,
    val region_id: Int,
    val name_uz: String,
    val created_at: String,
    var checked:Boolean = false
): Serializable
