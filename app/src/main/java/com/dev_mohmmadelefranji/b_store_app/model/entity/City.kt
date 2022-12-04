package com.dev_mohmmadelefranji.b_store_app.model.entity

import com.google.gson.annotations.SerializedName

data class City(
    @SerializedName("id")
    var id: Int,
    @SerializedName("name_en")
    var nameEn: String,
    @SerializedName("name_ar")
    var nameAr: String
)