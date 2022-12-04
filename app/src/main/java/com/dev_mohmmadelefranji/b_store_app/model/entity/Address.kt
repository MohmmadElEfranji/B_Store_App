package com.dev_mohmmadelefranji.b_store_app.model.entity

import com.google.gson.annotations.SerializedName

data class Address(
    @SerializedName("id") var addressID: Int,
    @SerializedName("name") var name: String,
    @SerializedName("info") var info: String,
    @SerializedName("contact_number") var contactNumber: String? = null,
    @SerializedName("lat") var lat: String? = null,
    @SerializedName("lang") var lang: String? = null,
    @SerializedName("city_id") var cityId: Int? = null,
    @SerializedName("city") var city: City
)