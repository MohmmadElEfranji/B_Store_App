package com.dev_mohmmadelefranji.b_store_app.model.remote.api.api_services

import com.dev_mohmmadelefranji.b_store_app.model.remote.api.response.payment_card_response.PaymentCardResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers

interface PaymentCardServiceApi {

    @Headers(
        "X-Requested-With: XMLHttpRequest"
    )
    @GET("payment-cards")
    suspend fun getAllPaymentCards(
        @Header("Authorization")
        token: String,
    ): Response<PaymentCardResponse>
}