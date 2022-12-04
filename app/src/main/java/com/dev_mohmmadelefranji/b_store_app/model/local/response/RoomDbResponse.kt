package com.dev_mohmmadelefranji.b_store_app.model.local.response

sealed class RoomDbResponse<out T> {

    data class Success<T>(val data: T?) : RoomDbResponse<T>()

    data class Failure(val message: String?) : RoomDbResponse<Nothing>()

    object Loading : RoomDbResponse<Nothing>()

    object Empty : RoomDbResponse<Nothing>()
}