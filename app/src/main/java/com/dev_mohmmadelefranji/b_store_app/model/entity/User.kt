package com.dev_mohmmadelefranji.b_store_app.model.entity

import com.google.gson.annotations.SerializedName

data class User(

    @SerializedName("id") var id: Int,
    @SerializedName("name") var name: String,
    @SerializedName("email") var email: String,
    @SerializedName("mobile") var mobile: String,
    @SerializedName("gender") var gender: String,
    @SerializedName("active") var active: Boolean,
    @SerializedName("verified") var verified: Boolean,
    @SerializedName("city_id") var cityId: String,
    @SerializedName("store_id") var storeId: String,
    @SerializedName("fcm_token") var fcmToken: String,
    @SerializedName("token") var token: String,
    @SerializedName("token_type") var tokenType: String,
    @SerializedName("refresh_token") var refreshToken: String,
    @SerializedName("city") var city: City,
    @SerializedName("store") var store: Store

)
