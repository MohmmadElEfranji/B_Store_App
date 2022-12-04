package com.dev_mohmmadelefranji.b_store_app.model.remote.api.repository

import com.dev_mohmmadelefranji.b_store_app.model.remote.api.api_services.retrofit_builder.RetrofitBuilder
import com.dev_mohmmadelefranji.b_store_app.model.remote.api.response.common_response.CommonResponse
import com.dev_mohmmadelefranji.b_store_app.model.remote.api.response.product_response.ProductResponse
import retrofit2.Response

class ProductRepository {

    suspend fun getFavoriteProducts(
        token: String,
    ): Response<ProductResponse> {
        return RetrofitBuilder.product.getFavoriteProducts(token = "Bearer $token")
    }

    suspend fun addAndRemoveFavoriteProducts(
        token: String,
        productID: Int
    ): Response<CommonResponse> {
        return RetrofitBuilder.homeApi
            .addAndRemoveFavoriteProducts(
                token = "Bearer $token",
                productID
            )
    }
}