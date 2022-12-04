package com.dev_mohmmadelefranji.b_store_app.model.remote.api.response.common_response

import com.google.gson.annotations.SerializedName

data class CommonResponse(
    @SerializedName("status")
    val status: Boolean,
    @SerializedName("message")
    val message: String
)
