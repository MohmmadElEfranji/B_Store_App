package com.dev_mohmmadelefranji.b_store_app.model.remote.api.repository

import com.dev_mohmmadelefranji.b_store_app.model.remote.api.api_services.retrofit_builder.RetrofitBuilder
import com.dev_mohmmadelefranji.b_store_app.model.remote.api.response.category_response.CategoryResponse
import retrofit2.Response

class CategoryRepository {

    suspend fun getSubCategory(
        token: String,
        categoryID: Int
    ): Response<CategoryResponse> {
        return RetrofitBuilder.categoryApi.getSubCategory(
            token = "Bearer $token",
            categoryID
        )
    }
}