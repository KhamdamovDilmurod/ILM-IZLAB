package com.example.ilm_izlab.model

import java.io.Serializable

data class CategoryModel (
    val id: Int,
    val title: String,
    val icon: String,
    val created_at: String,
    val updated_at: String,
    val sciences: List<SciencesModel>,
    var isBtnVisibility: Boolean = false
):Serializable
