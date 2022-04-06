package com.example.ilm_izlab.model.request

data class ResetPasswordRequest(
    val phone: String,
    val password: String,
    val sms_code: String
)