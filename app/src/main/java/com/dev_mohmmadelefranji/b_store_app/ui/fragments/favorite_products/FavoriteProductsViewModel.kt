package com.dev_mohmmadelefranji.b_store_app.ui.fragments.favorite_products

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dev_mohmmadelefranji.b_store_app.model.remote.api.repository.ProductRepository
import com.dev_mohmmadelefranji.b_store_app.model.remote.api.response.common_response.ApiResponse
import com.dev_mohmmadelefranji.b_store_app.model.remote.api.response.common_response.CommonResponse
import com.dev_mohmmadelefranji.b_store_app.model.remote.api.response.product_response.ProductResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.json.JSONObject

class FavoriteProductsViewModel(private val repo: ProductRepository) : ViewModel() {

    //region getFavoriteProducts
    private val _getFavoriteProducts: MutableStateFlow<ApiResponse<ProductResponse>> =
        MutableStateFlow(ApiResponse.Empty())

    val getFavoriteProducts = _getFavoriteProducts.asStateFlow()


    fun getFavoriteProducts(token: String) {
        viewModelScope.launch {
            try {
                _getFavoriteProducts.value = ApiResponse.Loading()
                val response =

                    repo.getFavoriteProducts(token = token)

                when {
                    response.isSuccessful -> {
                        val data = response.body()
                        data?.let {
                            _getFavoriteProducts.value = ApiResponse.Success(it)
                        }
                    }

                    else -> {
                        val jObjError = JSONObject(response.errorBody()!!.string())
                        val errorMsg = jObjError.getString("message")
                        _getFavoriteProducts.value = ApiResponse.Failure(errorMsg)
                    }
                }

            } catch (exception: Exception) {
                _getFavoriteProducts.value = ApiResponse.Failure(message = exception.message!!)
            }
        }
    }

    //endregion


    //region addAndRemoveFavoriteProducts
    private val _addAndRemoveFavoriteProducts: MutableStateFlow<ApiResponse<CommonResponse>> =
        MutableStateFlow(ApiResponse.Empty())

    val addAndRemoveFavoriteProducts = _addAndRemoveFavoriteProducts.asStateFlow()


    fun addAndRemoveFavoriteProducts(token: String, productID: Int) {
        viewModelScope.launch {
            try {
                _addAndRemoveFavoriteProducts.value = ApiResponse.Loading()
                val response =
                    repo.addAndRemoveFavoriteProducts(
                        token = token,
                        productID = productID
                    )

                when {
                    response.isSuccessful -> {
                        val data = response.body()
                        data?.let {
                            _addAndRemoveFavoriteProducts.value = ApiResponse.Success(it)
                        }
                    }

                    else -> {
                        val jObjError = JSONObject(response.errorBody()!!.string())
                        val errorMsg = jObjError.getString("message")
                        _addAndRemoveFavoriteProducts.value = ApiResponse.Failure(errorMsg)
                    }
                }

            } catch (exception: Exception) {
                _addAndRemoveFavoriteProducts.value =
                    ApiResponse.Failure(message = exception.message!!)
            }
        }
    }

    //endregion
}