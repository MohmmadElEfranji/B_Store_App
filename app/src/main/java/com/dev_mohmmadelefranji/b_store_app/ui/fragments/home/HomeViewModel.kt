package com.dev_mohmmadelefranji.b_store_app.ui.fragments.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dev_mohmmadelefranji.b_store_app.model.remote.api.repository.HomeScreenRepository
import com.dev_mohmmadelefranji.b_store_app.model.remote.api.response.common_response.ApiResponse
import com.dev_mohmmadelefranji.b_store_app.model.remote.api.response.common_response.CommonResponse
import com.dev_mohmmadelefranji.b_store_app.model.remote.api.response.home_response.HomeScreenResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.json.JSONObject

class HomeViewModel(private val repo: HomeScreenRepository) : ViewModel() {

    //region getDataOfHomeScreen
    private val _getDataOfHomeScreen: MutableStateFlow<ApiResponse<HomeScreenResponse>> =
        MutableStateFlow(ApiResponse.Empty())

    val getDataOfHomeScreen = _getDataOfHomeScreen.asStateFlow()


    fun getDataOfHomeScreen(token: String) {
        viewModelScope.launch {
            try {
                _getDataOfHomeScreen.value = ApiResponse.Loading()
                val response = repo.getDataOfHomeScreen(token = token)

                when {
                    response.isSuccessful -> {
                        val data = response.body()
                        data?.let {
                            _getDataOfHomeScreen.value = ApiResponse.Success(it)
                        }
                    }

                    else -> {
                        val jObjError = JSONObject(response.errorBody()!!.string())
                        val errorMsg = jObjError.getString("message")
                        _getDataOfHomeScreen.value = ApiResponse.Failure(errorMsg)
                    }
                }

            } catch (exception: Exception) {
                _getDataOfHomeScreen.value = ApiResponse.Failure(message = exception.message!!)
            }
        }
    }

    //endregion

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
}