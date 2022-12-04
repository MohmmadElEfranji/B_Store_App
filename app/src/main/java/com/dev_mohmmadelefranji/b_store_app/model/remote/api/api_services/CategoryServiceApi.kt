package com.dev_mohmmadelefranji.b_store_app.model.remote.api.api_services

import com.dev_mohmmadelefranji.b_store_app.model.remote.api.response.category_response.CategoryResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Path

interface CategoryServiceApi {

    @Headers(
        "X-Requested-With: XMLHttpRequest"
    )
    @GET("categories/{id}")
    suspend fun getSubCategory(
        @Header("Authorization")
        token: String,
        @Path("id") id: Int
    ): Response<CategoryResponse>
}