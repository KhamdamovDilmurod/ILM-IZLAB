package com.example.ilm_izlab.model

import java.io.Serializable

data class CoursesModel(
    val id: Int,
    val center_id: Int,
    val science_id: Int,
    val name: String,
    val monthly_payment: Long,
    val created_at: String,
    val updated_at: String,
    val science: SciencesModel
): Serializable

/*
          "id": 30,
          "center_id": 26,
          "science_id": 10,
          "name": "Matematika",
          "monthly_payment": 200000,
          "created_at": "2022-01-25T07:10:13.000000Z",
          "updated_at": null,
          "science": {
            "id": 10,
            "category_id": 3,
            "title": "Matematika",
            "icon": "upload/science/icon/OCNrWBXnKOmfEJjb7jCB.png",
            "created_at": "2022-01-21T06:05:47.000000Z",
            "updated_at": null
 */