package com.dev_mohmmadelefranji.b_store_app.model.remote.api.repository

import com.dev_mohmmadelefranji.b_store_app.model.remote.api.api_services.retrofit_builder.RetrofitBuilder
import com.dev_mohmmadelefranji.b_store_app.model.remote.api.response.auth_response.AuthResponse
import com.dev_mohmmadelefranji.b_store_app.model.remote.api.response.cities_response.CitiesResponse
import com.dev_mohmmadelefranji.b_store_app.model.remote.api.response.common_response.CommonResponse
import retrofit2.Response

class AuthRepository {

    //region sign up && sign in

    suspend fun signUp(
        userName: String,
        mobileNumber: String,
        password: String,
        gender: String,
        storeApiKey: String,
        cityID: Int,
    ): Response<AuthResponse> {
        return RetrofitBuilder.authApi.signUp(
            userName,
            mobileNumber,
            password,
            gender,
            storeApiKey,
            cityID
        )
    }

    suspend fun signIn(
        mobileNumber: String,
        password: String,
        fcmToken: String
    ): Response<AuthResponse> {
        return RetrofitBuilder.authApi.signIn(mobileNumber, password, fcmToken)
    }


    suspend fun activateAccount(
        mobileNumber: String,
        activateCode: String,
    ): Response<CommonResponse> {
        return RetrofitBuilder.authApi.activateAccount(
            mobileNumber,
            activateCode
        )
    }

    suspend fun forgetPassword(
        mobileNumber: String,
    ): Response<AuthResponse> {
        return RetrofitBuilder.authApi.forgetPassword(mobileNumber = mobileNumber)
    }

    suspend fun resetPassword(
        mobileNumber: String,
        verificationCode: String,
        password: String,
        confirmPassword: String,
    ): Response<CommonResponse> {
        return RetrofitBuilder.authApi.resetPassword(
            mobileNumber,
            verificationCode,
            password,
            confirmPassword
        )
    }

    suspend fun signOut(
        token: String
    ): Response<CommonResponse> {
        return RetrofitBuilder.authApi.signOut("Bearer $token")
    }

    suspend fun getAllCities(): Response<CitiesResponse> {
        return RetrofitBuilder.authApi.getAllCities()
    }
    //endregion
}