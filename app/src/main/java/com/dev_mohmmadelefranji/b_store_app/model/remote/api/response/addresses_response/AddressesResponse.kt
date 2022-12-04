package com.dev_mohmmadelefranji.b_store_app.model.remote.api.response.addresses_response

import com.dev_mohmmadelefranji.b_store_app.model.entity.Address
import com.google.gson.annotations.SerializedName

data class AddressesResponse(

    @SerializedName("status") var status: Boolean,
    @SerializedName("message") var message: String,
    @SerializedName("list") var listOfAddress: ArrayList<Address> = arrayListOf()
)