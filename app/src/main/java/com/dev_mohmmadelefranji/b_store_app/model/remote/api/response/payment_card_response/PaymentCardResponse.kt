package com.dev_mohmmadelefranji.b_store_app.model.remote.api.response.payment_card_response

import com.dev_mohmmadelefranji.b_store_app.model.entity.PaymentCard
import com.google.gson.annotations.SerializedName

data class PaymentCardResponse(

    @SerializedName("status") var status: Boolean,
    @SerializedName("message") var message: String,
    @SerializedName("list") var paymentCardList: MutableList<PaymentCard> = arrayListOf()
)