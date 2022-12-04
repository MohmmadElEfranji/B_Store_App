package com.dev_mohmmadelefranji.b_store_app.model.remote.api.response.category_response

import com.dev_mohmmadelefranji.b_store_app.model.entity.Category
import com.google.gson.annotations.SerializedName

data class CategoryResponse(

    @SerializedName("status") var status: Boolean,
    @SerializedName("message") var message: String,
    @SerializedName("list") var listOfCategory: ArrayList<Category> = arrayListOf()
)