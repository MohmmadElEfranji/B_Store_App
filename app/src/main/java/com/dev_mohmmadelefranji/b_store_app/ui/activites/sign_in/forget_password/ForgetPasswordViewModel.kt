package com.dev_mohmmadelefranji.b_store_app.ui.activites.sign_in.forget_password

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dev_mohmmadelefranji.b_store_app.model.remote.api.repository.AuthRepository
import com.dev_mohmmadelefranji.b_store_app.model.remote.api.response.auth_response.AuthResponse
import com.dev_mohmmadelefranji.b_store_app.model.remote.api.response.common_response.ApiResponse
import com.dev_mohmmadelefranji.b_store_app.model.remote.api.response.common_response.CommonResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.json.JSONObject

class ForgetPasswordViewModel(private val repo: AuthRepository) : ViewModel() {

    //region get Verification Code
    private val _getVerificationCode: MutableStateFlow<ApiResponse<AuthResponse>> =
        MutableStateFlow(ApiResponse.Empty())
    val getVerificationCode = _getVerificationCode.asStateFlow()

    fun sendVerificationCode(
        mobileNumber: String,
    ) {
        viewModelScope.launch {
            try {
                _getVerificationCode.value = ApiResponse.Loading()

                val response = repo.forgetPassword(mobileNumber)

                when {
                    response.isSuccessful -> {
                        val data = response.body()
                        data?.let {
                            _getVerificationCode.value = ApiResponse.Success(it)
                        }
                    }

                    else -> {
                        val jObjError = JSONObject(response.errorBody()!!.string())
                        val errorMsg = jObjError.getString("message")
                        _getVerificationCode.value = ApiResponse.Failure(errorMsg)
                    }
                }
            } catch (exception: Exception) {
                _getVerificationCode.value = ApiResponse.Failure(message = exception.message!!)
            }
        }
    }
    //endregion

    //region Reset Password
    private val _resetPassword: MutableStateFlow<ApiResponse<CommonResponse>> =
        MutableStateFlow(ApiResponse.Empty())
    val resetPassword = _resetPassword.asStateFlow()

    fun resetPassword(
        mobileNumber: String,
        verificationCode: String,
        password: String,
        confirmPassword: String,
    ) {
        viewModelScope.launch {
            try {

                _resetPassword.value = ApiResponse.Loading()

                val response = repo.resetPassword(
                    mobileNumber,
                    verificationCode,
                    password,
                    confirmPassword
                )

                when {
                    response.isSuccessful -> {
                        val data = response.body()
                        data?.let {

                            _resetPassword.value = (ApiResponse.Success(it))
                        }
                    }

                    else -> {
                        val jObjError = JSONObject(response.errorBody()!!.string())
                        val errorMsg = jObjError.getString("message")
                        _resetPassword.value = (ApiResponse.Failure(errorMsg))
                    }
                }
            } catch (exception: Exception) {
                _resetPassword.value = ApiResponse.Failure(message = exception.message!!)
            }
        }
    }
    //endregion
}