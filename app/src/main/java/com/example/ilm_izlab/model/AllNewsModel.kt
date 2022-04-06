package com.example.ilm_izlab.model

import java.io.Serializable

data class AllNewsModel(
    val id: Int,
    val center_id: Int,
    val title: String,
    val image: String,
    val content:String,
    val status: String,
    val date: String,
    val center_image: String,
    val center_name: String,
    val district_name: String
):Serializable
