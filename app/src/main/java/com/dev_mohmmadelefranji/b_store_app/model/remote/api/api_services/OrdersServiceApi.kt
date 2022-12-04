package com.dev_mohmmadelefranji.b_store_app.model.remote.api.api_services

import com.dev_mohmmadelefranji.b_store_app.model.remote.api.response.addresses_response.AddressesResponse
import com.dev_mohmmadelefranji.b_store_app.model.remote.api.response.common_response.CommonResponse
import retrofit2.Response
import retrofit2.http.*

interface OrdersServiceApi {

    @Headers(
        "Accept: application/json",
        "lang: ar",

        )
    @FormUrlEncoded
    @POST("orders")
    suspend fun sendOrder(
        @Header("Authorization")
        token: String,
        @Field("cart") cart: String,
        @Field("payment_type") paymentType: String,
        @Field("address_id") addressID: Int,
    ): Response<CommonResponse>


    @Headers(
        "X-Requested-With: XMLHttpRequest"
    )
    @GET("addresses")
    suspend fun getAllAddresses(
        @Header("Authorization")
        token: String
    ): Response<AddressesResponse>
}