package com.dev_mohmmadelefranji.b_store_app.model.remote.api.api_services

import com.dev_mohmmadelefranji.b_store_app.model.remote.api.response.common_response.CommonResponse
import com.dev_mohmmadelefranji.b_store_app.model.remote.api.response.product_response.ProductResponse
import retrofit2.Response
import retrofit2.http.*

interface ProductServiceApi {

    @Headers(
        "Accept: application/json"
    )
    @GET("favorite-products")
    suspend fun getFavoriteProducts(
        @Header("Authorization")
        token: String,
    ): Response<ProductResponse>


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