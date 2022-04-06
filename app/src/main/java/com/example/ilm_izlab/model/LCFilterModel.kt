package com.example.ilm_izlab.model

import java.io.Serializable

data class LCFilterModel (
    var region_id: Int,
    var district_id: Int,
    var category_id: Int,
    var science_id: Int,
    var keyword: String,
    var sort: String,
    var limit: Int,
    var latitude: Double,
    var longitude: Double,
    var is_subscriber: Boolean
        ): Serializable