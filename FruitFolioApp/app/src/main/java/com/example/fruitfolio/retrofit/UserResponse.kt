package com.example.fruitfolio.retrofit

import java.io.Serializable

data class UserResponse (
    val accessToken: String,
    val refreshToken: String
) : Serializable