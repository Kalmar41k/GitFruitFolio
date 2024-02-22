package com.example.fruitfolio.retrofit

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface MainApi {
    @POST("auth/register")
    suspend fun register(@Body registerRequest: RegisterRequest): Response<UserResponse>

    @POST("auth/authenticate")
    suspend fun authenticate(@Body authenticateRequest: AuthenticateRequest): Response<UserResponse>

    @GET("sorts/byProductClass/{productClass}")
    suspend fun getProducts(
        @Path("productClass") productClass: String,
        @Header("Authorization") authorization: String
    ): Response<List<Product>>
}