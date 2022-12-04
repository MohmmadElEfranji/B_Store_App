package com.dev_mohmmadelefranji.b_store_app.model.remote.api.api_services

import com.dev_mohmmadelefranji.b_store_app.model.remote.api.response.auth_response.AuthResponse
import com.dev_mohmmadelefranji.b_store_app.model.remote.api.response.cities_response.CitiesResponse
import com.dev_mohmmadelefranji.b_store_app.model.remote.api.response.common_response.CommonResponse
import retrofit2.Response
import retrofit2.http.*

sealed interface AuthServiceApi {

    //region Auth request

    @FormUrlEncoded
    @POST(value = "auth/register")
    suspend fun signUp(
        @Field("name") userName: String,
        @Field("mobile") mobileNumber: String,
        @Field("password") password: String,
        @Field("gender") gender: String,
        @Field("STORE_API_KEY") storeApiKey: String,
        @Field("city_id") cityID: Int,
    ): Response<AuthResponse>

    @Headers(
        "lang: ar"
    )
    @FormUrlEncoded
    @POST(value = "auth/login")
    suspend fun signIn(
        @Field("mobile") mobileNumber: String,
        @Field("password") password: String,
        @Field("fcm_token") fcmToken: String,
    ): Response<AuthResponse>

    @Headers(
        "lang: ar"
    )
    @FormUrlEncoded
    @POST(value = "auth/activate")
    suspend fun activateAccount(
        @Field("mobile") mobileNumber: String,
        @Field("code") activateCode: String,
    ): Response<CommonResponse>


    @Headers(
        "lang: ar"
    )
    @FormUrlEncoded
    @POST(value = "auth/forget-password")
    suspend fun forgetPassword(
        @Field("mobile") mobileNumber: String,
    ): Response<AuthResponse>


    @Headers(
        "lang: ar"
    )
    @FormUrlEncoded
    @POST(value = "auth/reset-password")
    suspend fun resetPassword(
        @Field("mobile") mobileNumber: String,
        @Field("code") verificationCode: String,
        @Field("password") password: String,
        @Field("password_confirmation") confirmPassword: String,
    ): Response<CommonResponse>


    @Headers(
        "Accept: application/json"
    )
    @GET("students/auth/logout")
    suspend fun signOut(
        @Header("Authorization")
        token: String
    ): Response<CommonResponse>


    @Headers(
        "Accept: application/json"
    )
    @GET("cities")
    suspend fun getAllCities(): Response<CitiesResponse>
//endregion
}