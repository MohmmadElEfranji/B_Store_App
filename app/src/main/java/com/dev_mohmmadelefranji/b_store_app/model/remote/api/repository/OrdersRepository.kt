package com.dev_mohmmadelefranji.b_store_app.model.remote.api.repository

import com.dev_mohmmadelefranji.b_store_app.model.remote.api.api_services.retrofit_builder.RetrofitBuilder
import com.dev_mohmmadelefranji.b_store_app.model.remote.api.response.addresses_response.AddressesResponse
import com.dev_mohmmadelefranji.b_store_app.model.remote.api.response.common_response.CommonResponse
import retrofit2.Response

class OrdersRepository {

    suspend fun getAllAddresses(
        token: String
    ): Response<AddressesResponse> {
        return RetrofitBuilder.orderApi.getAllAddresses(token = "Bearer $token")
    }

    suspend fun sendOrder(
        token: String,
        cart: String,
        paymentType: String,
        addressID: Int,
    ): Response<CommonResponse> {
        return RetrofitBuilder.orderApi.sendOrder(
            token = "Bearer $token",
            cart,
            paymentType,
            addressID
        )
    }
}