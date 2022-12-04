package com.dev_mohmmadelefranji.b_store_app.model.entity

import com.google.gson.annotations.SerializedName

data class Store(
    @SerializedName("id") var id: Int,
    @SerializedName("name") var name: String,
    @SerializedName("store_name") var storeName: String,
    @SerializedName("email") var email: String,
    @SerializedName("email_verified_at") var emailVerifiedAt: String,
    @SerializedName("mobile") var mobile: String,
    @SerializedName("store_uuid") var storeUuid: String,
    @SerializedName("city_id") var cityId: String,
    @SerializedName("verification_code") var verificationCode: String,
    @SerializedName("active") var active: String,
    @SerializedName("verified") var verified: String,
    @SerializedName("firebase_key") var firebaseKey: String,
    @SerializedName("image") var image: String,
    @SerializedName("address") var address: String,
    @SerializedName("facebook") var facebook: String,
    @SerializedName("instagram") var instagram: String,
    @SerializedName("created_at") var createdAt: String,
    @SerializedName("updated_at") var updatedAt: String
)