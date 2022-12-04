package com.dev_mohmmadelefranji.b_store_app.model.remote.api.response.auth_response

import com.dev_mohmmadelefranji.b_store_app.model.entity.User
import com.google.gson.annotations.SerializedName

data class AuthResponse(
    @SerializedName("status")
    val status: Boolean,
    @SerializedName("message")
    val message: String,
    @SerializedName("code")
    val code: Int,
    @SerializedName("data") var user: User
)
