package com.dev_mohmmadelefranji.b_store_app.model.remote.api.response.product_response

import com.dev_mohmmadelefranji.b_store_app.model.entity.Product
import com.google.gson.annotations.SerializedName

data class ProductResponse(

    @SerializedName("status") var status: Boolean,
    @SerializedName("message") var message: String,
    @SerializedName("list") var listOfProduct: ArrayList<Product> = arrayListOf()
)