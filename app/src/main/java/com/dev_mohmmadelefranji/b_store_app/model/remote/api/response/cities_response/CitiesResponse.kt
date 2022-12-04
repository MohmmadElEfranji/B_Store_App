package com.dev_mohmmadelefranji.b_store_app.model.remote.api.response.cities_response

import com.dev_mohmmadelefranji.b_store_app.model.entity.City
import com.google.gson.annotations.SerializedName

data class CitiesResponse(
    @SerializedName("status")
    val status: Boolean,
    @SerializedName("message")
    val message: String,
    @SerializedName("list")
    var citiesList: ArrayList<City> = arrayListOf()
)
