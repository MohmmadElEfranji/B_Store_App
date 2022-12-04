package com.dev_mohmmadelefranji.b_store_app.ui.fragments.order_fragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dev_mohmmadelefranji.b_store_app.model.remote.api.repository.OrdersRepository
import com.dev_mohmmadelefranji.b_store_app.model.remote.api.response.addresses_response.AddressesResponse
import com.dev_mohmmadelefranji.b_store_app.model.remote.api.response.common_response.ApiResponse
import com.dev_mohmmadelefranji.b_store_app.model.remote.api.response.common_response.CommonResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.json.JSONObject

class OrderViewModel(private val repo: OrdersRepository) : ViewModel() {


    //region getAllAddresses
    private val _getAllAddresses: MutableStateFlow<ApiResponse<AddressesResponse>> =
        MutableStateFlow(ApiResponse.Empty())

    val getAllAddresses = _getAllAddresses.asStateFlow()


    fun getAllAddresses(token: String) {
        viewModelScope.launch {
            try {
                _getAllAddresses.value = ApiResponse.Loading()
                val response = repo.getAllAddresses(token = token)

                when {
                    response.isSuccessful -> {
                        val data = response.body()
                        data?.let {
                            _getAllAddresses.value = ApiResponse.Success(it)
                        }
                    }

                    else -> {
                        val jObjError = JSONObject(response.errorBody()!!.string())
                        val errorMsg = jObjError.getString("message")
                        _getAllAddresses.value = ApiResponse.Failure(errorMsg)
                    }
                }

            } catch (exception: Exception) {
                _getAllAddresses.value = ApiResponse.Failure(message = exception.message!!)
            }
        }
    }

    //endregion


    //region getAllAddresses
    private val _sendOrder: MutableStateFlow<ApiResponse<CommonResponse>> =
        MutableStateFlow(ApiResponse.Empty())

    val sendOrder = _sendOrder.asStateFlow()


    fun sendOrder(
        token: String,
        cart: String,
        paymentType: String,
        addressID: Int,
    ) {
        viewModelScope.launch {
            try {
                _sendOrder.value = ApiResponse.Loading()
                val response = repo.sendOrder(
                    token = token,
                    cart,
                    paymentType,
                    addressID
                )

                when {
                    response.isSuccessful -> {
                        val data = response.body()
                        data?.let {
                            _sendOrder.value = ApiResponse.Success(it)
                        }
                    }

                    else -> {
                        val jObjError = JSONObject(response.errorBody()!!.string())
                        val errorMsg = jObjError.getString("message")
                        _sendOrder.value = ApiResponse.Failure(errorMsg)
                    }
                }

            } catch (exception: Exception) {
                _sendOrder.value = ApiResponse.Failure(message = exception.message!!)
            }
        }
    }

    //endregion

}