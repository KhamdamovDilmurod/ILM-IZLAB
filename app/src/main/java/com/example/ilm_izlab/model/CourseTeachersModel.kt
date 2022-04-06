package com.example.ilm_izlab.model

import java.io.Serializable

data class CourseTeachersModel (
    val id: Int,
    val center_id: Int,
    val name: String,
    val info_link: String,
    val specialization: String,
    val experience: Int,
    val avatar: String,
    val created_at: String,
    val updated_at: String
        ): Serializable
/*
       "id": 17,
   "center_id": 26,
   "name": "Xonqulov Sherali",
   "info_link": "https://t.me/matematikaUZBEK",
   "specialization": "Dektarant",
   "experience": 18,
   "avatar": "upload/training_center/teacher/avatar/SpWNPbP07kBr18MLlluF.jpg",
   "created_at": "2022-01-25T07:22:05.000000Z",
   "updated_at": null
  */