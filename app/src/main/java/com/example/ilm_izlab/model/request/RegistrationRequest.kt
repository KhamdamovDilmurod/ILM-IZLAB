package com.example.ilm_izlab.model.request

data class RegistrationRequest (
    val phone: String,
    val password: String,
    val sms_code: String,
    val fullname: String
        )