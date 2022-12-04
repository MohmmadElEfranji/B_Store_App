package com.dev_mohmmadelefranji.b_store_app.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dev_mohmmadelefranji.b_store_app.model.remote.api.repository.NotificationRepository
import com.dev_mohmmadelefranji.b_store_app.model.remote.api.response.common_response.ApiResponse
import com.dev_mohmmadelefranji.b_store_app.model.remote.api.response.notification_response.NotificationResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.json.JSONObject

class NotificationViewModel(private val repo: NotificationRepository) : ViewModel() {


    //region getDataOfHomeScreen
    private val _getAllNotifications: MutableStateFlow<ApiResponse<NotificationResponse>> =
        MutableStateFlow(ApiResponse.Empty())

    val getAllNotifications = _getAllNotifications.asStateFlow()


    fun getAllNotifications(token: String) {
        viewModelScope.launch {
            try {
                _getAllNotifications.value = ApiResponse.Loading()
                val response = repo.getAllNotifications(token = token)

                when {
                    response.isSuccessful -> {
                        val data = response.body()
                        data?.let {
                            _getAllNotifications.value = ApiResponse.Success(it)
                        }
                    }

                    else -> {
                        val jObjError = JSONObject(response.errorBody()!!.string())
                        val errorMsg = jObjError.getString("message")
                        _getAllNotifications.value = ApiResponse.Failure(errorMsg)
                    }
                }

            } catch (exception: Exception) {
                _getAllNotifications.value = ApiResponse.Failure(message = exception.message!!)
            }
        }
    }

    //endregion

}