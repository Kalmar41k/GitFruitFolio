package com.example.fruitfolio.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitService {
    val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("http://192.168.0.109:8080/api/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}