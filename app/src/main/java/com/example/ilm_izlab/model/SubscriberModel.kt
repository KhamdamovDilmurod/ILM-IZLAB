package com.example.ilm_izlab.model

data class SubscriberModel(
    val id: Int,
    val user_id:Int,
    val center_id: Int,
    val created_at: String,
    val updated_at: String,
    val date: String,
    val user_fullname: String,
    val user_avatar: Any
)