package com.dev_mohmmadelefranji.b_store_app.model.remote.api.response.common_response

sealed class ApiResponse<T> {

    data class Success<T>(val data: T?) : ApiResponse<T>()

    data class Failure<T>(val message: String?) : ApiResponse<T>()


    class Loading<T> : ApiResponse<T>()

    class Empty<T> : ApiResponse<T>()
}