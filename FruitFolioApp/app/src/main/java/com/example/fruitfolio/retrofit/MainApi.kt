package com.example.fruitfolio.retrofit

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
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

    @POST("grades/create")
    suspend fun createGrade(@Body gradeRequest: GradeRequest,
                            @Header("Authorization") authorization: String
    ): Response<Double>

    @GET("sorts/sort_comments/{id}")
    suspend fun getComments(
        @Path("id") productSortId: Int,
        @Header("Authorization") authorization: String
    ): Response<List<CommentResponse>>

    @POST("comments/create")
    suspend fun createComment(@Body commentRequest: CommentRequest,
                              @Header("Authorization") authorization: String
    ): Response<CommentResponse>

    @POST("comments/myComments")
    suspend fun getMyComments(@Body myCommentRequest: MyCommentRequest,
                              @Header("Authorization") authorization: String
    ): Response<List<MyCommentResponse>>

    @DELETE("comments/deleteById/{id}")
    suspend fun deleteMyComment(@Path("id") id: Int,
                                @Header("Authorization") authorization: String
    ): Response<Unit>
}