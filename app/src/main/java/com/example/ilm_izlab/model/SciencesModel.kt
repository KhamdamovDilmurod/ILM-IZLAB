package com.example.ilm_izlab.model

import java.io.Serializable

data class SciencesModel(
    val id: Int,
    val category_id: Int,
    val title: String,
    val icon: String,
    var created_at: String,
    var checked: Boolean = false
): Serializable

