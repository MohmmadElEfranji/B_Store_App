package com.dev_mohmmadelefranji.b_store_app.model.entity

import com.google.gson.annotations.SerializedName

data class Notification(

    @SerializedName("id")
    var id: Int,
    @SerializedName("title")
    var title: String,
    @SerializedName("subtitle")
    var subtitle: String,
    @SerializedName("body")
    var body: String,
    @SerializedName("sent_at")
    var sentAt: String,
    @SerializedName("user_id")
    var userId: String,
    @SerializedName("created_at")
    var createdAt: String,
    @SerializedName("updated_at")
    var updatedAt: String

)
