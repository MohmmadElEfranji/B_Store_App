package com.dev_mohmmadelefranji.b_store_app.ui.fragments.payment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dev_mohmmadelefranji.b_store_app.model.remote.api.repository.PaymentCardRepository
import com.dev_mohmmadelefranji.b_store_app.model.remote.api.response.common_response.ApiResponse
import com.dev_mohmmadelefranji.b_store_app.model.remote.api.response.payment_card_response.PaymentCardResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.json.JSONObject

class PaymentViewModel(private val repo: PaymentCardRepository) : ViewModel() {

    //region getAllPaymentCards
    private val _getAllPaymentCards: MutableStateFlow<ApiResponse<PaymentCardResponse>> =
        MutableStateFlow(ApiResponse.Empty())

    val getAllPaymentCards = _getAllPaymentCards.asStateFlow()


    fun getAllPaymentCards(token: String) {
        viewModelScope.launch {
            try {
                _getAllPaymentCards.value = ApiResponse.Loading()
                val response =

                    repo.getAllPaymentCards(token = token)

                when {
                    response.isSuccessful -> {
                        val data = response.body()
                        data?.let {
                            _getAllPaymentCards.value = ApiResponse.Success(it)
                        }
                    }

                    else -> {
                        val jObjError = JSONObject(response.errorBody()!!.string())
                        val errorMsg = jObjError.getString("message")
                        _getAllPaymentCards.value = ApiResponse.Failure(errorMsg)
                    }
                }

            } catch (exception: Exception) {
                _getAllPaymentCards.value = ApiResponse.Failure(message = exception.message!!)
            }
        }
    }

    //endregion

}