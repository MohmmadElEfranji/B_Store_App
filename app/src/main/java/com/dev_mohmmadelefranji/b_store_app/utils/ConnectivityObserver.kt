package com.dev_mohmmadelefranji.b_store_app.utils

import kotlinx.coroutines.flow.Flow


interface ConnectivityObserver {

    fun observer(): Flow<Status>

    enum class Status {
        Available, Unavailable, Losing, Lost
    }

}