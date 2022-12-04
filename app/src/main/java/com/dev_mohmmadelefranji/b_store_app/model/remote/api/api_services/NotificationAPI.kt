package com.dev_mohmmadelefranji.b_store_app.model.remote.api.api_services

import com.dev_mohmmadelefranji.b_store_app.model.remote.api.response.notification_response.NotificationResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers


interface NotificationAPI {

    @Headers(
        "Accept: application/json"
    )
    @GET("notifications")
    suspend fun getAllNotifications(
        @Header("Authorization")
        token: String
    ): Response<NotificationResponse>

}