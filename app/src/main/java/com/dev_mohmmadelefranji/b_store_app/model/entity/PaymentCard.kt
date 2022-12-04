package com.dev_mohmmadelefranji.b_store_app.model.entity

import com.google.gson.annotations.SerializedName

data class PaymentCard(
    @SerializedName("id") var id: Int,
    @SerializedName("type") var type: String,
    @SerializedName("holder_name") var holderName: String,
    @SerializedName("card_number") var cardNumber: String,
    @SerializedName("exp_date") var expDate: String,
    @SerializedName("cvv") var cvv: String,
    @SerializedName("user_id") var userId: String,
    @SerializedName("created_at") var createdAt: String,
    @SerializedName("updated_at") var updatedAt: String
)
