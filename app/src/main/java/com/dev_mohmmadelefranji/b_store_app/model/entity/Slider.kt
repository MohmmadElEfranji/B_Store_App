package com.dev_mohmmadelefranji.b_store_app.model.entity

import com.google.gson.annotations.SerializedName

data class Slider(
    @SerializedName("id")
    var id: Int,
    @SerializedName("object_id")
    var objectId: String,
    @SerializedName("url")
    var url: String,
    @SerializedName("image_url")
    var imageUrl: String
)
