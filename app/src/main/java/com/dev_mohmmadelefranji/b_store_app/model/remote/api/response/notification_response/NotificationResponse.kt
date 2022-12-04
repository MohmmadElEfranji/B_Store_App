package com.dev_mohmmadelefranji.b_store_app.model.remote.api.response.notification_response

import com.dev_mohmmadelefranji.b_store_app.model.entity.Notification
import com.google.gson.annotations.SerializedName

data class NotificationResponse(
    @SerializedName("status")
    var status: Boolean? = null,
    @SerializedName("message")
    var message: String? = null,
    @SerializedName("list")
    var notificationList: MutableList<Notification> = arrayListOf()

)