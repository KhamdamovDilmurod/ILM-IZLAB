package com.example.ilm_izlab.model


import java.io.Serializable

data class TrainingCentersModel(
    val id: Int,
    val region_id:Int,
    val district_id: Int,
    val name: String,
    val phone: String,
    val address: String,
    val comment: String,
    val monthly_payment_min: Long,
    val monthly_payment_max: Long,
    val main_image: String,
    val images: ArrayList<String>,
    val latitude: Double,
    val longitude: Double,
    val rating: Double,
    val rating_count: Int,
    val subsribers_count: Int,
    val district: DistrictModel,
    val region: RegionModel,
    val courses: List<CoursesModel>
) : Serializable
/*
{
      "id": 40,
      "region_id": 12,
      "district_id": 185,
      "name": "Friends school",
      "phone": "+998975552626",
      "address": "Asia uz supermarketi yonida",
      "comment": "Ingliz tili, Mental arifmetika, Prezident maktabiga tayyorlov va super matematika darslari",
      "monthly_payment_min": 200000,
      "monthly_payment_max": 520000,
      "main_image": "upload/training_center/main_image/QIJPHxitjQAqWFW2AO0N.jpg",
      "latitude": 40.38634545852676,
      "longitude": 71.78419589996338,
      "rating": 5,
      "rating_count": 1,
      "images": [],
      "subsribers_count": 0,
      "district": {
        "id": 185,
        "region_id": 12,
        "district_name": "Farg‘ona shahri"
      },
      "region": {
        "id": 12,
        "region_name": "Farg‘ona viloyati"
      },
      "courses": []
    }
 */