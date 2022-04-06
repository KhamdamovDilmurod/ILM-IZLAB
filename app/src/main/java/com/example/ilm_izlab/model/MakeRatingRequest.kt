package com.example.ilm_izlab.model

data class MakeRatingRequest(
    val rating: Float,
    val comment: String,
    val center_id: Int
)
