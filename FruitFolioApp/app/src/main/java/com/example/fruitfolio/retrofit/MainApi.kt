package com.example.fruitfolio.retrofit

import com.example.fruitfolio.retrofit.requests.AuthenticateRequest
import com.example.fruitfolio.retrofit.requests.CommentRequest
import com.example.fruitfolio.retrofit.requests.GradeRequest
import com.example.fruitfolio.retrofit.requests.MyCommentRequest
import com.example.fruitfolio.retrofit.requests.RegisterRequest
import com.example.fruitfolio.retrofit.responses.CommentResponse
import com.example.fruitfolio.retrofit.responses.MyCommentResponse
import com.example.fruitfolio.retrofit.responses.PredictResponse
import com.example.fruitfolio.retrofit.responses.Product
import com.example.fruitfolio.retrofit.responses.UserResponse
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
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

    @Multipart
    @POST("predict")
    suspend fun uploadImage(
        @Part image: MultipartBody.Part
    ): Response<PredictResponse>

    @POST("sorts/byDescription")
    suspend fun byDescription(@Body predictResponse: PredictResponse,
                              @Header("Authorization") authorization: String
    ): Response<Product>
}