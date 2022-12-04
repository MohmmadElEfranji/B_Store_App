package com.dev_mohmmadelefranji.b_store_app.model.remote.api.repository

import com.dev_mohmmadelefranji.b_store_app.model.remote.api.api_services.retrofit_builder.RetrofitBuilder
import com.dev_mohmmadelefranji.b_store_app.model.remote.api.response.notification_response.NotificationResponse
import retrofit2.Response

class NotificationRepository {

    suspend fun getAllNotifications(
        token: String
    ): Response<NotificationResponse> {
        return RetrofitBuilder.notification.getAllNotifications(token = "Bearer $token")
    }
}