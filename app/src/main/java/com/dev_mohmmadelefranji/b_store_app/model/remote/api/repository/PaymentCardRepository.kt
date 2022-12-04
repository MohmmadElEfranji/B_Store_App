package com.dev_mohmmadelefranji.b_store_app.model.remote.api.repository

import com.dev_mohmmadelefranji.b_store_app.model.remote.api.api_services.retrofit_builder.RetrofitBuilder
import com.dev_mohmmadelefranji.b_store_app.model.remote.api.response.payment_card_response.PaymentCardResponse
import retrofit2.Response

class PaymentCardRepository {

    suspend fun getAllPaymentCards(
        token: String,
    ): Response<PaymentCardResponse> {
        return RetrofitBuilder.paymentCard.getAllPaymentCards(
            token = "Bearer $token"
        )
    }
}