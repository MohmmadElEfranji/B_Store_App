package com.dev_mohmmadelefranji.b_store_app.ui.activites.sign_up.activate_account

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dev_mohmmadelefranji.b_store_app.model.remote.api.repository.AuthRepository
import com.dev_mohmmadelefranji.b_store_app.model.remote.api.response.common_response.ApiResponse
import com.dev_mohmmadelefranji.b_store_app.model.remote.api.response.common_response.CommonResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.json.JSONObject

class ActivateAccountViewModel(private val repo: AuthRepository) : ViewModel() {

    //region Activate Account
    private val _activateAccount: MutableStateFlow<ApiResponse<CommonResponse>> =
        MutableStateFlow(ApiResponse.Empty())

    val activateAccount = _activateAccount.asStateFlow()

    fun activateAccount(
        mobileNumber: String,
        activateCode: String,
    ) {
        viewModelScope.launch {
            try {
                _activateAccount.value = ApiResponse.Loading()
                val response = repo.activateAccount(mobileNumber, activateCode)

                when {
                    response.isSuccessful -> {
                        val data = response.body()
                        data?.let {
                            _activateAccount.value = ApiResponse.Success(it)
                        }
                    }

                    else -> {
                        val jObjError = JSONObject(response.errorBody()!!.string())
                        val errorMsg = jObjError.getString("message")
                        _activateAccount.value = ApiResponse.Failure(errorMsg)
                    }
                }

            } catch (exception: Exception) {
                _activateAccount.value = ApiResponse.Failure(message = exception.message!!)
            }
        }
    }
    //endregion
}