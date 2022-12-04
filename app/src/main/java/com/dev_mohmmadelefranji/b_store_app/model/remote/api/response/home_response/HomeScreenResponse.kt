package com.dev_mohmmadelefranji.b_store_app.model.remote.api.response.home_response

import com.dev_mohmmadelefranji.b_store_app.model.entity.DataOfHomeScreen
import com.google.gson.annotations.SerializedName

data class HomeScreenResponse(


    @SerializedName("status") var status: Boolean? = null,
    @SerializedName("message") var message: String? = null,
    @SerializedName("data") var data: DataOfHomeScreen
)
