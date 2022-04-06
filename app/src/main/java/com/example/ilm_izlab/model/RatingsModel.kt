package com.example.ilm_izlab.model

import java.io.Serializable

data class RatingsModel(
    val id: Int,
    val user_id: String,
    val rating: Float,
    val comment: String,
    val created_at: String,
    val date: String,
    val user_fullname: String,
    val user_avatar: Any?
):Serializable
/*
    {
      "id": 80,
      "user_id": 83,
      "rating": 3.5,
      "comment": "amazing",
      "created_at": "2022-03-11T04:28:59.000000Z",
      "date": "09:03 11/03/2022",
      "user_fullname": "Dilmurod",
      "user_avatar": null
    }
 */
