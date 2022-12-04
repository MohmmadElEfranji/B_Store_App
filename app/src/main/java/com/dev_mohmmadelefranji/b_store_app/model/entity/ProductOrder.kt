package com.dev_mohmmadelefranji.b_store_app.model.entity

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class ProductOrder(


    @SerializedName("product_id") var productId: Int,
    @SerializedName("quantity") var quantity: Int
) : Parcelable