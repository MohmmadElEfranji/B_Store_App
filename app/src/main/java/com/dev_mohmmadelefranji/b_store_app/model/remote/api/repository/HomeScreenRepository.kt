package com.dev_mohmmadelefranji.b_store_app.model.remote.api.repository

import com.dev_mohmmadelefranji.b_store_app.model.remote.api.api_services.retrofit_builder.RetrofitBuilder
import com.dev_mohmmadelefranji.b_store_app.model.remote.api.response.common_response.CommonResponse
import com.dev_mohmmadelefranji.b_store_app.model.remote.api.response.home_response.HomeScreenResponse
import retrofit2.Response

class HomeScreenRepository {

    suspend fun getDataOfHomeScreen(
        token: String
    ): Response<HomeScreenResponse> {
        return RetrofitBuilder.homeApi.getDataOfHomeScreen(token = "Bearer $token")
    }


    suspend fun addAndRemoveFavoriteProducts(
        token: String,
        productID: Int
    ): Response<CommonResponse> {
        return RetrofitBuilder.homeApi
            .addAndRemoveFavoriteProducts(
                token = "Bearer $token",
                productID
            )
    }
}