package com.example.ilm_izlab.model.response

data class RegistrationModel(
    val token: String,
    val phone: String,
    val password: String,
    val sms_code: String,
    val fullname: String,
    val status:String
)
