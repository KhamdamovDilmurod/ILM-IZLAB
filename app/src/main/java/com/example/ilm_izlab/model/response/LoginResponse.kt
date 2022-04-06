package com.example.ilm_izlab.model.response

data class LoginResponse(
    var token: String,
    var fullname: String,
    var phone: String,
    var avatar:String,
    var status: String
)
