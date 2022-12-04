package com.dev_mohmmadelefranji.b_store_app.model.remote.api.api_services

import com.dev_mohmmadelefranji.b_store_app.model.remote.api.response.common_response.CommonResponse
import com.dev_mohmmadelefranji.b_store_app.model.remote.api.response.home_response.HomeScreenResponse
import retrofit2.Response
import retrofit2.http.*

interface HomeServiceApi {

    @Headers(
        "Accept: application/json"
    )
    @GET("home")
    suspend fun getDataOfHomeScreen(
        @Header("Authorization")
        token: String
    ): Response<HomeScreenResponse>


    @Headers(
        "X-Requested-With: XMLHttpRequest"
    )
    @FormUrlEncoded
    @POST("favorite-products")
    suspend fun addAndRemoveFavoriteProducts(
        @Header("Authorization")
        token: String,
        @Field("product_id")
        productID: Int
    ): Response<CommonResponse>
}